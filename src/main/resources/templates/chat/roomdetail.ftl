<!doctype html>
<html lang="en">
<head>
    <title>Websocket ChatRoom</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <style>
        [v-cloak] {
            display: none;
        }
    </style>
</head>
<body>
<div class="container" id="app" v-cloak>
    <div class="row">
        <div class="col-md-6">
            <h3>{{roomName}}</h3>
        </div>
        <div class="col-md-6 text-right">
            <a class="btn btn-primary btn-sm" href="/logout">로그아웃</a>
        </div>
    </div>
    <div class="input-group">
        <div class="input-group-prepend">
            <label class="input-group-text">내용</label>
        </div>
        <input type="text" class="form-control" v-model="message" v-on:keypress.enter="sendMessage('TALK')">
        <div class="input-group-append">
            <button class="btn btn-primary" type="button" @click="sendMessage('TALK')">보내기</button>
        </div>
    </div>
    <ul class="list-group">
        <li class="list-group-item" v-for="message in messages">
<#--새로운 필드 추가-->
            <div>
                <strong>{{message.sender}}</strong>
                <small class="text-muted">({{ formatTimestamp(message.timestamp) }})</small>
            </div>
            <div>{{message.message}}</div>
<#--            -->
        </li>
    </ul>
</div>
<!-- JavaScript -->
<script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
<script src="/webjars/axios/0.17.1/dist/axios.min.js"></script>
<script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/2.3.3-1/stomp.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
<script>
    //alert(document.title);
    // websocket & stomp initialize
    var sock = new SockJS("/ws-stomp");
    var ws = Stomp.over(sock);
    var reconnect = 0;
    // vue.js
    var vm = new Vue({
        el: '#app',
        data: {
            roomId: '',
            roomName: '',
            message: '',
            messages: [],
            token:''
        },
        created() {
            this.roomId = localStorage.getItem('wschat.roomId');
            this.roomName = localStorage.getItem('wschat.roomName');

            if (!this.roomId || !this.roomName) {
                alert("잘못된 접근입니다. 채팅방 정보를 확인해주세요.");
                location.href = "/chat/room"; // 채팅방 목록으로 리다이렉트
                return;
            }

            var _this = this;

            axios.get('/chat/user')
                .then(response => {
                    _this.token = response.data.token;

                    // 인증 토큰이 없을 경우 처리
                    if (!_this.token) {
                        alert("인증 토큰이 없습니다. 다시 로그인하세요.");
                        location.href = "/login";
                        return;
                    }

                    ws.connect({ "token": _this.token }, function(frame) {
                        ws.subscribe("/sub/chat/room/" + _this.roomId, function(message) {
                            var recv = JSON.parse(message.body);
                            _this.recvMessage(recv);
                        });

                        // 채팅방 입장 메시지 전송
                        _this.sendMessage('ENTER');
                    }, function(error) {
                        alert("서버 연결에 실패하였습니다. 다시 접속해 주십시오.");
                        location.href = "/chat/room";
                    });
                })
                .catch(error => {
                    console.error("사용자 정보 가져오기 실패:", error);
                    alert("사용자 정보를 불러올 수 없습니다. 다시 로그인하세요.");
                    location.href = "/login";
                });
        },
        methods: {
            formatTimestamp: function(timestamp) {
                const date = new Date(timestamp); // ISO 8601 형식으로 변환된 문자열
                return date.toLocaleString();     // 로컬 시간 형식으로 변환
            },
            findRoom: function() {
                axios.get('/chat/room/'+this.roomId).then(response => { this.room = response.data; });
            },
            sendMessage: function(type) {
                if (!type) {
                    console.error("메시지 타입이 정의되지 않았습니다.");
                    alert("메시지 타입이 유효하지 않습니다. 다시 시도하세요.");
                    return;
                }

                ws.send("/pub/chat/message", {"token": this.token}, JSON.stringify({
                    type: type, // 함수 인자로 받은 메시지 타입
                    roomId: this.roomId, // 현재 채팅방 ID
                    message: this.message, // 입력된 메시지
                    timestamp: new Date().toISOString() // ISO 8601 형식의 타임스탬프
                }));

                this.message = ''; // 메시지 전송 후 입력 필드 초기화
            },
            recvMessage: function(recv) {
                recv.timestamp = moment(recv.timestamp).format('YYYY-MM-DD HH:mm:ss');
                this.messages.unshift({
                    "type":recv.type,
                    "sender":recv.sender,
                    "message":recv.message,
                    // 새로운 필드 추가
                    "timestamp": recv.timestamp
                })
            }
        }
    });

    function connect() {
        // pub/sub event
        ws.connect({}, function(frame) {
            ws.subscribe("/sub/chat/room/"+vm.$data.roomId, function(message) {
                var recv = JSON.parse(message.body);
                vm.recvMessage(recv);
            });
            ws.send("/pub/chat/message", {}, JSON.stringify({type:'ENTER', roomId:vm.$data.roomId, sender:vm.$data.sender}));
        }, function(error) {
            if(reconnect++ <= 5) {
                setTimeout(function() {
                    console.log("connection reconnect");
                    sock = new SockJS("/ws-stomp");
                    ws = Stomp.over(sock);
                    connect();
                },10*1000);
            }
        });
    }
    connect();
</script>
</body>
</html>