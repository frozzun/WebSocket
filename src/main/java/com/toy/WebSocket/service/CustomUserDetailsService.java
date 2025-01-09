package com.toy.WebSocket.service;

import com.toy.WebSocket.entity.Member;
import com.toy.WebSocket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * loadUserByUsername 메서드를 오버라이드 하는데 여기서 넘겨받은 UserDetails와 Authentication의 패스워드를 비교하고 검증하는 로직을 처리한다.
 * DB에 User값이 존재하면 UserDetails 객체를 만들어서 리턴한다.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return memberRepository.findByEmail(username)
      .map(this::createUserDetails)
      .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
  }

  // DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
  private UserDetails createUserDetails(Member member) {
    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getAuthority().toString());

    return new User(
      String.valueOf(member.getId()),
      member.getPassword(),
      Collections.singleton(grantedAuthority)
    );
  }
}
