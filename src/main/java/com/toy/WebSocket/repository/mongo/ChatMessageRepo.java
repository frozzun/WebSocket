package com.toy.WebSocket.repository.mongo;

import com.toy.WebSocket.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * MongoDB
 */
public interface ChatMessageRepo extends MongoRepository<ChatMessage, String> {
  List<ChatMessage> findByRoomId(String roomId);
}
