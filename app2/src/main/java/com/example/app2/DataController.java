package com.example.app2;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
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
  Data test(@RequestBody Data data, @RequestHeader Map<String, String> headers) {
    logger.error(headers.toString());
    return data;
  }

  @PostMapping("/data1")
  Data test1(@RequestBody Data data, @RequestHeader Map<String, String> headers) {
    logger.error(headers.toString());
    NewRelicUtils.continueDistributedTransaction(data.getTraceId());
    data.setTraceId(NewRelicUtils.createDistributedTrace());
    template.postForEntity("http://localhost:8082/data1", data, Data.class);
    return data;
  }
}
