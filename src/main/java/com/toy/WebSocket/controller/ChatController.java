package com.toy.WebSocket.controller;

import com.toy.WebSocket.dto.ChatMessageDto;
import com.toy.WebSocket.service.ChatMessageService;
import com.toy.WebSocket.service.ChatRoomService;
import com.toy.WebSocket.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

  private final ChatRoomService chatRoomService;
  private final ChatMessageService chatMessageService;
  private final JwtTokenProvider jwtTokenProvider;
  private final RedisTemplate<String, Object> redisTemplate;
  private final ChannelTopic channelTopic;

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
    chatMessageService.publishChatMessage(messageDto);
    log.info("published Chat Message: {}", messageDto);

//    // Websocket에 발행된 메시지를 redis로 발행한다(publish)
//    redisTemplate.convertAndSend(channelTopic.getTopic(), messageDto);
  }
}
