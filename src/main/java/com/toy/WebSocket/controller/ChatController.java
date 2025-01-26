package com.toy.WebSocket.controller;

import com.toy.WebSocket.dto.ChatMessageDto;
import com.toy.WebSocket.service.ChatMessageService;
import com.toy.WebSocket.service.ChatRoomService;
import com.toy.WebSocket.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  /**
   * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
   */
  @MessageMapping("/chat/message")
  public void message(ChatMessageDto messageDto, @Header("token") String token) {
    String nickname = jwtTokenProvider.getUserNameFromJwt(token);
    messageDto.setSender(nickname);
    messageDto.setUserCount(chatRoomService.getUserCount(messageDto.getRoomId()));

    chatMessageService.publishChatMessage(messageDto);
    log.info("published Chat Message: {}", messageDto);
  }
}
