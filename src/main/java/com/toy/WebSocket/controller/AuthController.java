package com.toy.WebSocket.controller;

import com.toy.WebSocket.dto.MemberRequestDto;
import com.toy.WebSocket.dto.MemberResponseDto;
import com.toy.WebSocket.dto.TokenDto;
import com.toy.WebSocket.dto.TokenRequestDto;
import com.toy.WebSocket.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원가입 / 로그인 / 재발급을 처리하는 API이다.
 * SecurityConfig에서/auth/** 요청은 전부 허용했기 때문에 토큰 검증 로직을 타지 않는다.
 * MemberRequestDto 에는 사용자가 로그인 시도한 ID / PW String 이 존재한다.
 * TokenRequestDto 에는 재발급을 위한 AccessToken / RefreshToken String 이 존재한다.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
    return ResponseEntity.ok(authService.signup(memberRequestDto));
  }

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto) {
    return ResponseEntity.ok(authService.login(memberRequestDto));
  }

  @PostMapping("/reissue")
  public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
    return ResponseEntity.ok(authService.reissue(tokenRequestDto));
  }
}
