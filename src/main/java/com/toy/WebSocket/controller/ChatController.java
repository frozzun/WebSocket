package com.toy.WebSocket.controller;

import com.toy.WebSocket.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class ChatController {

  private final SimpMessageSendingOperations messagingTemplate;

  @MessageMapping("/chat/message")
  public void message(ChatMessageDto messageDto) {
    if (ChatMessageDto.MessageType.ENTER.equals(messageDto.getType()))
      messageDto.setMessage(messageDto.getSender() + "님이 입장하셨습니다.");
    messagingTemplate.convertAndSend("/sub/chat/room/" + messageDto.getRoomId(), messageDto);
  }
}
