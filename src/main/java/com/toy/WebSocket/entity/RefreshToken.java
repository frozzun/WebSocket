package com.toy.WebSocket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * key 에는 Member ID 값이 들어간다.
 * value 에는 Refresh Token String 이 들어간다.
 */
@Getter
@NoArgsConstructor
@Entity
public class RefreshToken {

  @Id
  @Column(name = "rt_key")
  private String key;

  @Column(name = "rt_value")
  private String value;

  @Builder
  public RefreshToken(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public RefreshToken updateValue(String token) {
    this.value = token;
    return this;
  }
}
