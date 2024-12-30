package com.toy.WebSocket.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ChatRoomDto {
  private String roomId;
  private String name;

  public static ChatRoomDto create(String name) {
    return ChatRoomDto.builder()
      .roomId(UUID.randomUUID().toString())
      .name(name)
      .build();
  }
}