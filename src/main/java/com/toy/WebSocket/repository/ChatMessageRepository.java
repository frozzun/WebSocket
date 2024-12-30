package com.toy.WebSocket.repository;

import com.toy.WebSocket.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * MongoDB
 */
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
}
