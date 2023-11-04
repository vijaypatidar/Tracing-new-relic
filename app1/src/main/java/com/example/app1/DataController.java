package com.example.app1;

import com.example.NewRelicUtils;
import com.newrelic.api.agent.ConcurrentHashMapHeaders;
import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DataController {

  RestTemplate template = new RestTemplateBuilder().build();
  Logger logger = LoggerFactory.getLogger(DataController.class);

  @Autowired
  MessageSender messageSender;

  @PostMapping("/data")
  Data test1(@RequestBody Data data) throws IOException {
    messageSender.send(data);
    return data;
  }
}
