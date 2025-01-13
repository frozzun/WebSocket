package com.toy.WebSocket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenDto {

  private String grantType;            // 토큰 유형 (예: Bearer)
  private String accessToken;          // 액세스 토큰
  private Long accessTokenExpiresIn;   // 액세스 토큰 만료 시간 (타임스탬프)
  private String refreshToken;         // 리프레시 토큰

  @Builder
  public TokenDto(String grantType, String accessToken, Long accessTokenExpiresIn, String refreshToken) {
    this.grantType = grantType;
    this.accessToken = accessToken;
    this.accessTokenExpiresIn = accessTokenExpiresIn;
    this.refreshToken = refreshToken;
  }
}
