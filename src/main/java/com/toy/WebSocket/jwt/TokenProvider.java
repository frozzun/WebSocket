package com.toy.WebSocket.jwt;

import com.toy.WebSocket.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * JWT 토큰에 관련된 암호화, 복호화, 검증 로직은 다 이곳에서 이루어진다.
 * 생성자
 *  application.yml 에 정의해 놓은 jwt.secret값을 가져와서 JWT를 만들 때 사용하는 암호화 키값을 생성합니다.
 * generateTokenDto
 *  유저 정보를 넘겨받아서 Access Token과 Refresh Token을 생성한다.
 *  넘겨받은 유저 정보의 authentication.getName() 메서드가 username을 가져온다.
 *  username으로 Member ID를 저장했기 때문에 해당 값이 설정된다.
 *  Access Token 에는 유저와 권한 정보를 담고 Refresh Token 에는 아무 정보도 담지 않는다.
 * getAuthentication
 *  JWT 토큰을 복호화하여 토큰에 들어 있는 정보를 꺼낸다.
 *  Access Token 에만 유저 정보를 담기 때문에 명시적으로 accessToken을 파라미터로 받는다.
 *  Refresh Token 에는 아무런 정보 없이 만료일자만 담았다.
 *  UserDetails 객체를 생생성해서 UsernamePasswordAuthenticationToken 형태로 리턴하는데 SecurityContext를 사용하기 위한 절차이다.
 * validateToken
 *  토큰 정보를 검증한다.
 *  Jwts 모듈이 알아서 Exception을 던진다.
 */
@Slf4j
@Component
public class TokenProvider {

  private static final String AUTHORITIES_KEY = "auth";
  private static final String BEARER_TYPE = "Bearer";
  private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
  private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

  private final Key key;

  public TokenProvider(@Value("${jwt.secret}") String secretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public TokenDto generateTokenDto(Authentication authentication) {
    // 권한들 가져오기
    String authorities = authentication.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));

    long now = (new Date()).getTime();

    // Access Token 생성
    Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
    String accessToken = Jwts.builder()
      .setSubject(authentication.getName())       // payload "sub": "name"
      .claim(AUTHORITIES_KEY, authorities)        // payload "auth": "ROLE_USER"
      .setExpiration(accessTokenExpiresIn)        // payload "exp": 151621022 (ex)
      .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
      .compact();

    // Refresh Token 생성
    String refreshToken = Jwts.builder()
      .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
      .signWith(key, SignatureAlgorithm.HS512)
      .compact();

    return TokenDto.builder()
      .grantType(BEARER_TYPE)
      .accessToken(accessToken)
      .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
      .refreshToken(refreshToken)
      .build();
  }

  public Authentication getAuthentication(String accessToken) {
    // 토큰 복호화
    Claims claims = parseClaims(accessToken);

    if (claims.get(AUTHORITIES_KEY) == null) {
      throw new RuntimeException("권한 정보가 없는 토큰입니다.");
    }

    // 클레임에서 권한 정보 가져오기
    Collection<? extends GrantedAuthority> authorities =
      Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

    // UserDetails 객체를 만들어서 Authentication 리턴
    UserDetails principal = new User(claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, "", authorities);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      log.info("잘못된 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {
      log.info("만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      log.info("지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalArgumentException e) {
      log.info("JWT 토큰이 잘못되었습니다.");
    }
    return false;
  }

  private Claims parseClaims(String accessToken) {
    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }
}
