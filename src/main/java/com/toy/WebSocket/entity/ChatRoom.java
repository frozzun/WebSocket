package com.toy.WebSocket.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("chatroom")
public class ChatRoom implements Serializable {

  @Serial
  private static final long serialVersionUID = 6494678977089006639L;

  @Id
  private String roomId;
  private String name;
  private List<Long> participants = new ArrayList<>(); // 참가자 목록 (User ID)
  private List<String> session= new ArrayList<>(); // 참가한 sessionId 목록
  private long userCount;
//  private String lastMessage; // 마지막 메시지 -> 나중에 추가
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static ChatRoom create(String name) {
    ChatRoom chatRoom =new ChatRoom();
    chatRoom.roomId = UUID.randomUUID().toString();
    chatRoom.name = name;
    chatRoom.userCount = 0L;
    chatRoom.createdAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    chatRoom.updatedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    chatRoom.participants = new ArrayList<>();
    chatRoom.session = new ArrayList<>();
    return chatRoom;
  }

  // 입장
  public void addParticipant(long userId) {
    if (!participants.contains(userId)) {
      participants.add(userId);
    }
  }
  public void addSession(String sessionId) {
    if (!session.contains(sessionId)) {
      session.add(sessionId);
    }
  }

  // 퇴장
  public void removeParticipant(long userId) {
    participants.remove(userId);
  }
  public void removeSession(String sessionId) {
    session.remove(sessionId);
  }

  public void addUserCount() {
    this.userCount++;
  }
  public void removeUserCount() {
    if (this.userCount > 0) {
      this.userCount--;
    }
  }
}

