package com.toy.WebSocket.service;

import com.toy.WebSocket.entity.ChatRoom;
import com.toy.WebSocket.repository.mongo.ChatRoomMongoRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatRoomServiceTest {
  @Mock
  private ChatRoomMongoRepo chatRoomMongoRepo;  // Mock 객체

  @InjectMocks
  private ChatRoomService chatRoomService;

  private AutoCloseable mocks; // Mock 리소스 관리 객체


  @BeforeEach
  void setUp() {
    mocks = MockitoAnnotations.openMocks(this); // Mock 초기화
  }

  @AfterEach
  void tearDown() throws Exception {
    mocks.close(); // Mock 리소스 정리
  }

  @Test
  @DisplayName("모든 방 조회")
  void getAllRooms() {
    // Given
    ChatRoom room1 = ChatRoom.builder()
      .roomId("test-room-1")
      .name("room1")
      .userCount(0)
      .createdAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime())
      .updatedAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime())
      .participants(new ArrayList<>())
      .session(new ArrayList<>())
      .build();

    ChatRoom room2 = ChatRoom.builder()
      .roomId("test-room-2")
      .name("room2")
      .userCount(0)
      .createdAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime())
      .updatedAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime())
      .participants(new ArrayList<>())
      .session(new ArrayList<>())
      .build();

    when(chatRoomMongoRepo.findAll()).thenReturn(Arrays.asList(room1, room2));

    // When
    List<ChatRoom> rooms = chatRoomService.getAllRooms();

    // Then
    assertNotNull(rooms);
    assertEquals(2, rooms.size());
    assertEquals("test-room-1", rooms.get(0).getRoomId());
    assertEquals("test-room-2", rooms.get(1).getRoomId());
    verify(chatRoomMongoRepo, times(1)).findAll(); // findAll()이 한 번 호출되었는지 확인
  }

  @Test
  @DisplayName("roomId로 조회")
  void getRoomById() {
    // Given
    ChatRoom room = ChatRoom.builder()
      .roomId("test-room")
      .name("room")
      .userCount(0)
      .createdAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime())
      .updatedAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime())
      .participants(new ArrayList<>())
      .session(new ArrayList<>())
      .build();
    String roomId = room.getRoomId();
    when(chatRoomMongoRepo.findByRoomId(roomId)).thenReturn(room);

    // When
    ChatRoom result = chatRoomService.getRoomById(roomId);

    // Then
    assertNotNull(result);
    assertEquals("test-room", result.getRoomId());
    assertEquals("room", result.getName());
    verify(chatRoomMongoRepo, times(1)).findByRoomId(roomId);
  }

  @Test
  @DisplayName("destination 에서 roomId 추출")
  void getRoomId() {
    // Given
    String destination1 = "/chat/room/12345";
    String destination2 = "/chat/room/abcdef";
    String destination3 = "/chat/room/"; // 마지막에 '/'만 있는 경우
    String destination4 = "invalidString"; // '/'가 없는 경우

    // When
    String roomId1 = chatRoomService.getRoomId(destination1);
    String roomId2 = chatRoomService.getRoomId(destination2);
    String roomId3 = chatRoomService.getRoomId(destination3);
    String roomId4 = chatRoomService.getRoomId(destination4);

    // Then
    assertEquals("12345", roomId1);
    assertEquals("abcdef", roomId2);
    assertEquals("", roomId3);  // 마지막이 '/'로 끝나면 빈 문자열 반환
    assertEquals("", roomId4);  // '/'가 없으면 빈 문자열 반환
  }

  @Test
  @DisplayName("채팅방 생성")
  void createChatRoom() {
    // Given
    String roomName = "TestRoom";

    // When
    ChatRoom createdRoom = chatRoomService.createChatRoom(roomName);

    // Then
    assertNotNull(createdRoom);
    assertEquals(roomName, createdRoom.getName());
    verify(chatRoomMongoRepo, times(1)).save(any(ChatRoom.class)); // 저장 메서드가 호출되었는지 검증
  }

  @Test
  @DisplayName("채팅방에 있는 유저수 반환")
  void getUserCount() {
  }

  @Test
  @DisplayName("유저가 입장한 채팅방ID와 유저 세션ID 맵핑 정보 저장")
  void setUserEnterInfo() {
  }

  @Test
  @DisplayName("유저 세션으로 입장해 있는 채팅방 ID 조회")
  void getUserEnterRoomId() {
  }

  @Test
  @DisplayName("유저 세션정보와 맵핑된 채팅방ID 삭제")
  void removeUserEnterInfo() {
  }

  @Test
  @DisplayName("채팅방에 입장한 유저수 +1")
  void plusUserCount() {
  }

  @Test
  @DisplayName("채팅방에 입장한 유저수 -1")
  void minusUserCount() {
  }
}