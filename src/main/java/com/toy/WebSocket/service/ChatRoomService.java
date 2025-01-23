package com.toy.WebSocket.service;

import com.toy.WebSocket.entity.ChatRoom;
import com.toy.WebSocket.pubsub.RedisSubscriber;
import com.toy.WebSocket.repository.mongo.ChatRoomMongoRepo;
import com.toy.WebSocket.repository.redis.ChatRoomRedisRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

  private final ChatRoomRedisRepo chatRoomRedisRepo;
  private final ChatRoomMongoRepo chatRoomMongoRepo;

  private final RedisMessageListenerContainer redisMessageListener;
  private final RedisSubscriber redisSubscriber;
  private Map<String, ChannelTopic> topics = new HashMap<>();

  public List<ChatRoom> getAllRooms() {
    // return chatRoomMongoRepo.findAll();
    return chatRoomRedisRepo.findAllRoom();
  }

  public ChatRoom getRoomById(String id) {
     return chatRoomMongoRepo.findByRoomId(id);
//    return chatRoomRedisRepo.findRoomById(id);
  }

  public ChatRoom createChatRoom(String name) {
    ChatRoom chatRoom = ChatRoom.create(name);
    chatRoomMongoRepo.save(chatRoom);
    chatRoomRedisRepo.saveChatRoom(chatRoom);
    return chatRoom;
  }

  public ChannelTopic getTopic(String roomId) {
    return topics.get(roomId);
  }
}
