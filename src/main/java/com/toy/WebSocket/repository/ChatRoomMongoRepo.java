package com.toy.WebSocket.repository;

import com.toy.WebSocket.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRoomMongoRepo extends MongoRepository<ChatRoom, String> {

  List<ChatRoom> findAll();
  ChatRoom findByRoomId(String id);
}
