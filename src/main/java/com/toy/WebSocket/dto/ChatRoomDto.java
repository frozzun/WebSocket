package com.toy.WebSocket.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class ChatRoomDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 6494678977089006639L;

  private String roomId;
  private String name;
  private long userCount;
}