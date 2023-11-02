package com.example.app1;

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
  Data test(@RequestBody Data data) {
//    template.postForEntity("http://localhost:8080/data", data, Data.class);
    return data;
  }

  @PostMapping("/data1")
  Data test1(@RequestBody Data data) throws IOException {
    Segment postData = NewRelic.getAgent().getTransaction().startSegment("PostData");
    Data data1 = new Data();
    messageSender.send(data1);
    postData.addOutboundRequestHeaders(ConcurrentHashMapHeaders.buildFromFlatMap(HeaderType.MESSAGE,NewRelicUtils.createDistributedTrace()));
    postData.end();
    return data1;
  }
}
