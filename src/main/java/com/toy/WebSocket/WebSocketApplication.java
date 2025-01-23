package com.toy.WebSocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableJpaAuditing
@SpringBootApplication
public class WebSocketApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebSocketApplication.class, args);
	}
}
