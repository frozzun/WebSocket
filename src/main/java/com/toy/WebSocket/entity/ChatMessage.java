package com.toy.WebSocket.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collation = "chat")
public class ChatMessage {
  // 메시지 타입 : 입장, 채팅
  public enum MessageType {
    ENTER, TALK
  }
  private MessageType type; // 메시지 타입
  private String roomId; // 방번호
  private String sender; // 메시지 보낸사람
  private String message; // 메시지
}
