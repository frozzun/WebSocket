package com.toy.WebSocket.entity;

import com.toy.WebSocket.dto.ChatMessageDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@Document(collation = "chat")
public class ChatMessage {
  // 메시지 타입 : 입장, 채팅
  private ChatMessageDto.MessageType type; // 메시지 타입
  private String roomId; // 방번호
  private String sender; // 메시지 보낸사람
  private String message; // 메시지

  @Builder.Default
  private LocalDateTime timestamp = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();  // 메시지 전송 시간
}
