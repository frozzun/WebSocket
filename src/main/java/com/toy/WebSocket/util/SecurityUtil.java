package com.toy.WebSocket.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * JwtFilter에서 세팅한 유저 정보를 꺼낸다.
 * memberId를 저장하게 했으므로 꺼내서 Long 타입으로 파싱 하여 반환한다.
 * SecurityContext는 사용자의 정보를 저장한다.
 */
@Slf4j
public class SecurityUtil {

  private SecurityUtil() { }

  // SecurityContext 에 유저 정보가 저장되는 시점
  // Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장
  public static Long getCurrentMemberId() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getName() == null) {
      throw  new RuntimeException("Security Context 에 인증 정보가 없습니다.");
    }

    return Long.parseLong(authentication.getName());
  }
}
