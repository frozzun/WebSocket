package com.toy.WebSocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  // UserDetailsService 빈 정의 (In-memory Authentication)
  @Bean
  public UserDetailsService userDetailsService() {
    return new InMemoryUserDetailsManager(
      User.withUsername("happydaddy")
        .password(passwordEncoder().encode("1234"))
        .roles("USER")
        .build(),
      User.withUsername("angrydaddy")
        .password(passwordEncoder().encode("1234"))
        .roles("USER")
        .build(),
      User.withUsername("guest")
        .password(passwordEncoder().encode("1234"))
        .roles("GUEST")
        .build()
    );
  }

  // PasswordEncoder 빈 정의
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // SecurityFilterChain 빈 정의
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf().disable() // CSRF 비활성화
      .authorizeHttpRequests()
      .requestMatchers("/chat/**").hasRole("USER") // 특정 경로에 대한 접근 제한
      .anyRequest().permitAll() // 그 외 요청은 모두 허용
      .and()
      .formLogin() // 로그인 폼 허용
      .and()
      .headers().frameOptions().sameOrigin();

    return http.build();
  }

  // WebSecurityCustomizer로 특정 URL을 무시하는 설정
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers("/ignore1", "/ignore2");
  }
}
