package com.toy.WebSocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.WebSocket.dto.ChatMessageDto;
import com.toy.WebSocket.dto.ChatRoomDto;
import com.toy.WebSocket.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {
  private final ObjectMapper objectMapper;
  private final ChatService chatService;

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String payload = message.getPayload();
    log.info("payload {}", payload);
// 삭제        TextMessage textMessage = new TextMessage("Welcome chatting sever~^^ ");
// 삭제       session.sendMessage(textMessage);
    ChatMessageDto chatMessageDto = objectMapper.readValue(payload, ChatMessageDto.class);
    ChatRoomDto room = chatService.findRoomById(chatMessageDto.getRoomId());
    room.handleActions(session, chatMessageDto, chatService);
  }
}
