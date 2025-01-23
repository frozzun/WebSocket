package com.toy.WebSocket.service;

import com.toy.WebSocket.dto.ChatMessageDto;
import com.toy.WebSocket.entity.ChatMessage;
import com.toy.WebSocket.repository.mongo.ChatMessageRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.toy.WebSocket.entity.ChatMessage.fromDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageService {
  private final ChatMessageRepo chatMessageRepo;
  private final RedisTemplate<String, Object> redisTemplate;
  private final ChannelTopic channelTopic;

  public List<ChatMessage> getChatMessages(String roomId) {
    return chatMessageRepo.findByRoomId(roomId);
  }

  public void addChatMessage(ChatMessage chatMessage) {
    chatMessageRepo.save(chatMessage);
    log.info("Saved chat message: {}", chatMessage.getMessage());
  }

  public void publishChatMessage(ChatMessageDto messageDto) {
    ChatMessage message = fromDto(messageDto);
    redisTemplate.convertAndSend(channelTopic.getTopic(), messageDto);
    chatMessageRepo.save(message);
    log.info("Published, saved chat message: {}", message);
  }
}
