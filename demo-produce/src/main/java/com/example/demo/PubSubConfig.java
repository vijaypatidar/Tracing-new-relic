package com.example.demo;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

@Configuration
public class PubSubConfig {

  @Bean
  @ServiceActivator(inputChannel = "dummy")
  MessageHandler handlerDummy(PubSubTemplate template){
    PubSubMessageHandler handler = new PubSubMessageHandler(template, "dummy");
    handler.setSync(true);
    return handler;
  }
}
