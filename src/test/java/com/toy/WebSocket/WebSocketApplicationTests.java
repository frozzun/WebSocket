package com.toy.WebSocket;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootTest
@ActiveProfiles("test")
class WebSocketApplicationTests {

	@Test
	void contextLoads() throws InterruptedException {
		waitForDatabaseConnection();
	}

	private void waitForDatabaseConnection() throws InterruptedException {
		int retries = 5;
		while (retries-- > 0) {
			try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/qnlove", "qnlove", "chanyoup1@")) {
				if (conn != null) {
					System.out.println("Database connected successfully!");
					return;
				}
			} catch (SQLException e) {
				System.out.println("Waiting for database...");
				Thread.sleep(5000);  // 5초 대기 후 재시도
			}
		}
		throw new RuntimeException("Unable to connect to the database.");
	}
}
