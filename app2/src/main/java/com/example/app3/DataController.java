package com.example.app3;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DataController {
  Logger logger = LoggerFactory.getLogger(DataController.class);
  RestTemplate template = new RestTemplateBuilder().build();

  @PostMapping("/data")
  Data test1(@RequestBody Data data, @RequestHeader Map<String, String> headers) {
    logger.error("Data:{}, headers:{}", data, headers);
    Dummy dummy = new Dummy();
    dummy.setName(data.getUsername());
    dummy.setId(dummy.getId());
    template.postForEntity("http://localhost:8085/send", dummy, Dummy.class);
    return data;
  }
}
