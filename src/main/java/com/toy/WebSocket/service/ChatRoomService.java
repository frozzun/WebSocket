package com.toy.WebSocket.service;

import com.toy.WebSocket.entity.ChatRoom;
import com.toy.WebSocket.repository.mongo.ChatRoomMongoRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {

  private final ChatRoomMongoRepo chatRoomMongoRepo;

  public List<ChatRoom> getAllRooms() {
    return chatRoomMongoRepo.findAll();
  }

  public ChatRoom getRoomById(String id) {
     return chatRoomMongoRepo.findByRoomId(id);
  }

  /**
   * destination정보에서 roomId 추출
   */
  public String getRoomId(String destination) {
    int lastIndex = destination.lastIndexOf('/');
    if (lastIndex != -1)
      return destination.substring(lastIndex + 1);
    else
      return "";
  }

  public ChatRoom createChatRoom(String name) {
    ChatRoom chatRoom = ChatRoom.create(name);
    chatRoomMongoRepo.save(chatRoom);
    log.info("Chat room created: {}", chatRoom);
    return chatRoom;
  }

  public long getUserCount(String roomId) {
    ChatRoom chatRoom = chatRoomMongoRepo.findByRoomId(roomId);
    return chatRoom.getUserCount();
  }

  // 유저가 입장한 채팅방ID와 유저 세션ID 맵핑 정보 저장
  public void setUserEnterInfo(String sessionId, String roomId) {
    ChatRoom chatRoom = chatRoomMongoRepo.findByRoomId(roomId);
    chatRoom.addSession(sessionId);
    chatRoomMongoRepo.save(chatRoom);
    log.info("User entered room: {} {}", roomId, sessionId);
  }

  // 유저 세션으로 입장해 있는 채팅방 ID 조회
  public String getUserEnterRoomId(String sessionId) {
    ChatRoom chatRoom = chatRoomMongoRepo.findBySession(sessionId);
    return chatRoom.getRoomId();
  }

  // 유저 세션정보와 맵핑된 채팅방ID 삭제
  public void removeUserEnterInfo(String sessionId) {
    ChatRoom chatRoom = chatRoomMongoRepo.findBySession(sessionId);
    chatRoom.removeSession(sessionId);
    chatRoomMongoRepo.save(chatRoom);
    log.info("session removed: {}", sessionId);
  }

  // 채팅방에 입장한 유저수 +1
  public void plusUserCount(String roomId) {
    ChatRoom chatRoom = chatRoomMongoRepo.findByRoomId(roomId);
    chatRoom.addUserCount();
    chatRoomMongoRepo.save(chatRoom);
    log.info("Chat room plus user count");
  }

  // 채팅방에 입장한 유저수 -1
  public void minusUserCount(String roomId) {
    ChatRoom chatRoom = chatRoomMongoRepo.findByRoomId(roomId);
    chatRoom.removeUserCount();
    chatRoomMongoRepo.save(chatRoom);
    log.info("Chat room remove user count");
  }
}