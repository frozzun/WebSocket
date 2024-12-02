package com.toy.WebSocket.RabbitMQ;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 *
 * @author : frozzun
 * @filename :RabbitConfig.java
 * @since 10/31/24
 */
@Configuration
public class RabbitConfig {

  private final RabbitAdmin rabbitAdmin;

  public RabbitConfig(ConnectionFactory connectionFactory) {
    this.rabbitAdmin = new RabbitAdmin(connectionFactory);
  }

  @Bean
  public RabbitAdmin rabbitAdmin() {
    return rabbitAdmin;
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    return rabbitTemplate;
  }

  public Queue createQueue(String queueName) {
    Queue queue = new Queue(queueName, true);  // Durable한 큐 설정
    rabbitAdmin.declareQueue(queue);  // 큐를 동적으로 선언
    return queue;
  }
}
