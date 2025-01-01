package com.toy.WebSocket.service;

import com.toy.WebSocket.entity.ChatRoom;
import com.toy.WebSocket.pubsub.RedisSubscriber;
import com.toy.WebSocket.repository.ChatRoomRepository;
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

  private final ChatRoomRepository chatRoomRepository;
  private final RedisMessageListenerContainer redisMessageListener;
  private final RedisSubscriber redisSubscriber;
  private Map<String, ChannelTopic> topics = new HashMap<>();

  public List<ChatRoom> getAllRooms() {
    return chatRoomRepository.findAllRoom();
  }

  public ChatRoom getRoomById(String id) {
    return chatRoomRepository.findRoomById(id);
  }

  public ChatRoom createChatRoom(String name) {
    ChatRoom chatRoom = ChatRoom.create(name);
    chatRoomRepository.saveChatRoom(chatRoom);
    return chatRoom;
  }

  public void enterChatRoom(String roomId) {
    ChannelTopic topic = topics.get(roomId);
    if (topic == null) {
      topic = new ChannelTopic(roomId);
      redisMessageListener.addMessageListener(redisSubscriber, topic);
      topics.put(roomId, topic);
    }
  }

  public ChannelTopic getTopic(String roomId) {
    return topics.get(roomId);
  }
}
