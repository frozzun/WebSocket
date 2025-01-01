package com.toy.WebSocket.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Document(collation = "chatroom")
public class ChatRoom implements Serializable {

  @Serial
  private static final long serialVersionUID = 6494678977089006639L;

  private String roomId;
  private String name;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static ChatRoom create(String name) {
    ChatRoom chatRoom =new ChatRoom();
    chatRoom.roomId = UUID.randomUUID().toString();
    chatRoom.name = name;
    chatRoom.createdAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    chatRoom.updatedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    return chatRoom;
  }
}
