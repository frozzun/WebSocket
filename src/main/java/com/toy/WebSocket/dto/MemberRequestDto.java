package com.toy.WebSocket.dto;

import com.toy.WebSocket.entity.Member;
import com.toy.WebSocket.entity.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

  private String email;
  private String password;
  private String authority;  // Authority 필드를 추가

  // 회원가입 시, Member 엔티티로 변환하는 메소드
  public Member toMember(PasswordEncoder passwordEncoder) {
    return new Member(
      email,
      passwordEncoder.encode(password),
      Authority.valueOf(authority)  // 문자열로 들어온 authority를 Enum으로 변환
    );
  }

  // 로그인 시, UsernamePasswordAuthenticationToken 객체로 변환하는 메소드
  public UsernamePasswordAuthenticationToken toAuthentication() {
    return new UsernamePasswordAuthenticationToken(email, password);
  }
}
