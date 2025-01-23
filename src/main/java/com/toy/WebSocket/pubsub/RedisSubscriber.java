package com.toy.WebSocket.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.WebSocket.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber{

  private final ObjectMapper objectMapper;
  private final SimpMessageSendingOperations messagingTemplate;

  /**
   * 메시지 리스너에 메시지가 수신되면 sendMessage 가 수행된다
   * 수신된 메시지는 /sub/chat/room/{roomId} 를 구독한 websocket 클라이언트에게 메시지가 발송
   */
  public void sendMessage(String publishMessage) {
    try {
      // ChatMessageDto 객채로 맵핑
      ChatMessageDto messageDto = objectMapper.readValue(publishMessage, ChatMessageDto.class);
      // Websocket 구독자에게 채팅 메시지 Send
      messagingTemplate.convertAndSend("/sub/chat/room/" + messageDto.getRoomId(), messageDto);
      log.info("Subscribed message: " + messageDto.getMessage());
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
