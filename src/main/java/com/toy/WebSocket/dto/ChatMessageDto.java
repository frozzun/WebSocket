package com.toy.WebSocket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ChatMessageDto {
  // 메시지 타입 : 입장, 채팅
  public enum MessageType {
    ENTER, TALK
  }
  private MessageType type; // 메시지 타입
  private String roomId; // 방번호
  private String sender; // 메시지 보낸사람
  private String message; // 메시지

  @Builder.Default
  private LocalDateTime timestamp = LocalDateTime.now();  // 메시지 전송 시간
}
