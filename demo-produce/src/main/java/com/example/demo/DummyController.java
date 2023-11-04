package com.example.demo;

import com.example.NewRelicUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newrelic.api.agent.NewRelic;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DummyController {

  private final DummyEvent dummyEvent;
  private final ObjectMapper mapper;

  @PostMapping("/send")
  public Dummy send(@RequestBody Dummy dummy, @RequestHeader() Map<String, String> headers)
      throws JsonProcessingException {
    log.error("Message:{}",dummy);
    dummy.setId(UUID.randomUUID().toString());
    String message = mapper.writeValueAsString(dummy);
    Map<String, String> head = NewRelicUtils.createDistributedTrace();
    head.put("x-debug-trace", headers.getOrDefault("x-debug-trace", "false"));
    dummyEvent.send(message, head);
    return dummy;
  }


}
