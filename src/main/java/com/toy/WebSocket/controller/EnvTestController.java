package com.toy.WebSocket.controller;

import com.toy.WebSocket.entity.Authority;
import com.toy.WebSocket.entity.Member;
import com.toy.WebSocket.repository.mysql.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvTestController {

  private final MemberRepository memberRepository;

  @Autowired
  public EnvTestController(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;

  }

  @Value("${MYSQL_HOST:Environment variable not found!}")
  private String testEnvVar;

  @GetMapping("/env-test")
  public String getEnvVar() {
    return "TEST_ENV_VAR: " + testEnvVar;
  }

  @PostMapping("/create")
  public Member createMember(Member member) {
    Member member1 = Member.builder()
      .email(member.getEmail())
      .password(member.getPassword())
      .authority(Authority.ROLE_USER)
      .build();

    memberRepository.save(member1);

    return member1;
  }

  @GetMapping("/read")
  public Member getMember() {
    return memberRepository.findById(1L).get();
  }
}

