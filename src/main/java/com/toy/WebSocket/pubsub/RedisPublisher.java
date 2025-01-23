//package com.toy.WebSocket.pubsub;
//
//import com.toy.WebSocket.dto.ChatMessageDto;
//import com.toy.WebSocket.entity.ChatMessage;
//import com.toy.WebSocket.service.ChatMessageService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.stereotype.Service;
//
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class RedisPublisher {
//  private final RedisTemplate<String, Object> redisTemplate;
//  private final ChatMessageService chatMessageService;
//
//  /**
//   * 채팅방에 입장하여 메시지를 작성하면 해당 메시지를
//   * Redis Topic에 발생 하는 기능의 서비스.
//   * 이 서비스를 통해 메시지를 발행하면 대기하고 있던
//   * redis 구독 서비스가 메시지를 처리.
//   */
//  public void publish(ChannelTopic topic, ChatMessageDto messageDto) {
//    redisTemplate.convertAndSend(topic.getTopic(), messageDto);
//    log.info("Published message: {}", messageDto.getMessage());
//    // 메시지를 발행할 때 DB에 저장하게 함.
//    chatMessageService.addChatMessage(
//      ChatMessage.builder()
//        .type(messageDto.getType())
//        .message(messageDto.getMessage())
//        .sender(messageDto.getSender())
//        .roomId(messageDto.getRoomId())
//        .build()
//    );
//  }
//}
