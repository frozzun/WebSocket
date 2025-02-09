package com.toy.WebSocket.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;
  private String email;
  private String password;

  @Enumerated(EnumType.STRING)
  private Authority authority;

  @Builder
  public Member(String email, String password, Authority authority) {
    this.email = email;
    this.password = password;
    this.authority = authority;
  }
}
