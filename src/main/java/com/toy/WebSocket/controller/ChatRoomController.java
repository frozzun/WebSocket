package com.toy.WebSocket.controller;

import com.toy.WebSocket.dto.ChatRoomDto;
import com.toy.WebSocket.entity.ChatRoom;
import com.toy.WebSocket.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

//  private final ChatRoomRedisRepo chatRoomRepository;
  private final ChatRoomService chatRoomService;

  // 채팅 리스트 화면
  @GetMapping("/room")
  public String rooms(Model model) {
    return "/chat/room";
  }
  // 모든 채팅방 목록 반환
  @GetMapping("/rooms")
  @ResponseBody
  public List<ChatRoomDto> room() {
    List<ChatRoom> chatRooms =  chatRoomService.getAllRooms();
    return chatRooms.stream()
      .map(chatRoom -> ChatRoomDto.builder()
        .name(chatRoom.getName())
        .roomId(chatRoom.getRoomId())
        .build())  // 필요한 필드들로 변환
      .collect(Collectors.toList());
  }
  // 채팅방 생성
  @PostMapping("/room")
  @ResponseBody
  public ChatRoomDto createRoom(@RequestParam String name) {
    ChatRoom chatRoom =  chatRoomService.createChatRoom(name);
    return ChatRoomDto.builder()
      .name(chatRoom.getName())
      .roomId(chatRoom.getRoomId())
      .build();
  }
  // 채팅방 입장 화면
  @GetMapping("/room/enter/{roomId}")
  public String roomDetail(Model model, @PathVariable String roomId) {
    model.addAttribute("roomId", roomId);
    return "/chat/roomdetail";
  }
  // 특정 채팅방 조회 -> 오류 뜸.
  @GetMapping("/room/{roomId}")
  @ResponseBody
  public ChatRoomDto roomInfo(@PathVariable String roomId) {
    ChatRoom chatRoom =  chatRoomService.getRoomById(roomId);
    return ChatRoomDto.builder()
      .name(chatRoom.getName())
      .roomId(chatRoom.getRoomId())
      .build();
  }
}
