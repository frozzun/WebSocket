package com.toy.WebSocket.pubsub;

import com.toy.WebSocket.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisPublisher {
  private final RedisTemplate<String, Object> redisTemplate;

  /**
   * 채팅방에 입장하여 메시지를 작성하면 해당 메시지를
   * Redis Topic에 발생 하는 기능의 서비스.
   *
   * 이 서비스를 통해 메시지를 발행하면 대기하고 있던
   * redis 구독 서비스가 메시지를 처리.
   */
  public void publish(ChannelTopic topic, ChatMessageDto messageDto) {
    redisTemplate.convertAndSend(topic.getTopic(), messageDto);
  }
}
