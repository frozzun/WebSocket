package com.toy.WebSocket.controller;

import com.toy.WebSocket.dto.ChatMessageDto;
import com.toy.WebSocket.service.ChatRoomService;
import com.toy.WebSocket.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

  private final ChatRoomService chatRoomService;
  private final JwtTokenProvider jwtTokenProvider;
  private final RedisTemplate redisTemplate;
  private final ChannelTopic channelTopic;
//  private final ChatRoomRedisRepo chatRoomRepository;

  /**
   * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
   */
  @MessageMapping("/chat/message")
  public void message(ChatMessageDto messageDto, @Header("token") String token) {
    String nickname = jwtTokenProvider.getUserNameFromJwt(token);
    messageDto.setSender(nickname);

    if (ChatMessageDto.MessageType.ENTER.equals(messageDto.getType())) {
      messageDto.setSender("[알림]");
      messageDto.setMessage(nickname + "님이 입장하셨습니다.");
    }
    // Websocket에 발행된 메시지를 redis로 발행한다(publish)
    redisTemplate.convertAndSend(channelTopic.getTopic(), messageDto);
  }
}
