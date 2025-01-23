package com.toy.WebSocket.config;

import com.toy.WebSocket.config.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

  private final StompHandler stompHandler;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/sub");
    config.setApplicationDestinationPrefixes("/pub");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws-stomp")
      //배포 환경에서는 실제 도메인을 설정
      .setAllowedOriginPatterns("*")
      .withSockJS();
  }

  // StompHandler 가 Websocket 앞단에서 token 을 체크할 수 있도록 인터셉터로 설정
  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(stompHandler);
  }
}
