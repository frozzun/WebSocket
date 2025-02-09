package com.toy.WebSocket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
  // 메시지 타입 : 입장, 채팅
  public enum MessageType {
    ENTER, TALK, QUIT
  }
  private MessageType type; // 메시지 타입
  private String roomId; // 방번호
  private String sender; // 메시지 보낸사람
  private String message; // 메시지
}
