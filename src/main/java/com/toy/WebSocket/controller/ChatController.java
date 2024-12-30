package com.toy.WebSocket.controller;

import com.toy.WebSocket.dto.ChatRoomDto;
import com.toy.WebSocket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

  private final ChatService chatService;

  @PostMapping
  public ChatRoomDto createRoom(@RequestParam String name) {
    return chatService.createRoom(name);
  }

  @GetMapping
  public List<ChatRoomDto> findAllRoom() {
    return chatService.findAllRoom();
  }
}
