package com.toy.WebSocket.service;

import com.toy.WebSocket.dto.ChatMessageDto;
import com.toy.WebSocket.entity.ChatMessage;
import com.toy.WebSocket.repository.mongo.ChatMessageRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import static com.toy.WebSocket.entity.ChatMessage.fromDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageService {
  private final ChatMessageRepo chatMessageRepo;
  private final RedisTemplate<String, Object> redisTemplate;
  private final ChannelTopic channelTopic;

  /**
   * 채팅방에 메시지 전송
   * @param messageDto : message
   */
  public void publishChatMessage(ChatMessageDto messageDto) {
    ChatMessage message = fromDto(messageDto);

    if (ChatMessageDto.MessageType.ENTER.equals(messageDto.getType())) {
      messageDto.setMessage(messageDto.getSender() + "님이 방에 입장했습니다.");
      messageDto.setSender("[알림]");
    } else if (ChatMessageDto.MessageType.QUIT.equals(messageDto.getType())) {
      messageDto.setMessage(messageDto.getSender() + "님이 방에서 나갔습니다.");
      messageDto.setSender("[알림]");
    }

    redisTemplate.convertAndSend(channelTopic.getTopic(), messageDto);
    chatMessageRepo.save(message);
    log.info("Published, saved chat message: {}", message);
  }
}