package com.example.demo;

import java.util.Map;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Headers;

@MessagingGateway(defaultRequestChannel = "dummy")
public interface DummyEvent {
  void send(String message, @Headers Map<String, String> headers);
}
