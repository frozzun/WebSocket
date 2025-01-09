package com.toy.WebSocket.dto;

import com.toy.WebSocket.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {

  private String email;

  // Member 엔티티를 받아서 DTO로 변환하는 메소드
  public static MemberResponseDto of(Member member) {
    return new MemberResponseDto(member.getEmail());
  }
}
