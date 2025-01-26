package com.toy.WebSocket.repository.mongo;

import com.toy.WebSocket.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRoomMongoRepo extends MongoRepository<ChatRoom, String> {

  List<ChatRoom> findAll();
  ChatRoom findByRoomId(String id);
  ChatRoom findBySession(String sessionId); // session에 연결된 채팅방이 여러개면?
}
