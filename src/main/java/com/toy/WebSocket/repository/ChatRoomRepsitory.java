package com.toy.WebSocket.repository;

import com.toy.WebSocket.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomRepsitory extends MongoRepository<ChatRoom, String> {
}
