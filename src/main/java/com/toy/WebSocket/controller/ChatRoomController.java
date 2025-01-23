package com.toy.WebSocket.controller;

import com.toy.WebSocket.dto.ChatRoomDto;
import com.toy.WebSocket.dto.LoginInfo;
import com.toy.WebSocket.entity.ChatRoom;
import com.toy.WebSocket.service.ChatRoomService;
import com.toy.WebSocket.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

  //  private final ChatRoomRedisRepo chatRoomRepository;
  private final ChatRoomService chatRoomService;
  private final JwtTokenProvider jwtTokenProvider;

  // 로그인한 회원의 id 및 Jwt 토큰 정보를 조회할 수 있도록 추가
  @GetMapping("/user")
  @ResponseBody
  public LoginInfo getUserInfo() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String name = auth.getName();
    return LoginInfo.builder()
      .name(name)
      .token(jwtTokenProvider.generateToken(name))
      .build();
  }

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
    ChatRoomDto roomDto = ChatRoomDto.builder()
      .name(chatRoom.getName())
      .roomId(chatRoom.getRoomId())
      .build();

    log.info("createRoomDto: {}", roomDto);
    return roomDto;
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
