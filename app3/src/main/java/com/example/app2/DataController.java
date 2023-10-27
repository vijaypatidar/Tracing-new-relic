package com.example.app2;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {
  Logger logger = LoggerFactory.getLogger(DataController.class);

  @Autowired
  MessageReceiver messageReceiver;

  @PostMapping("/data")
  Data test(@RequestBody Data data, @RequestHeader Map<String, String> headers) {
    logger.error(headers.toString());
    return data;
  }

  @PostMapping("/data1")
  Data test1(@RequestBody Data data, @RequestHeader Map<String, String> headers) {
    logger.error(headers.toString());
    messageReceiver.run(data);
    return data;
  }


}
