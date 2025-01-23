package com.toy.WebSocket.config.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.toy.WebSocket.repository.mysql")  // JPA 리포지토리 패키지 지정
public class JpaConfig {
  // JPA 관련 설정 추가 가능
}
