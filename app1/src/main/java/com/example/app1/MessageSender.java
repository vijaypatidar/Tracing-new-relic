package com.example.app1;

import com.example.NewRelicUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newrelic.api.agent.Trace;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageSender {

  File parent = new File("/Users/vijaypatidar/IdeaProjects/Tracing/tmp");
  Executor executor = Executors.newSingleThreadExecutor();
  RestTemplate template = new RestTemplateBuilder().build();
  @Autowired
  ObjectMapper mapper;

  @Trace
  public void send(Data data) throws IOException {
    Map<String, String> distributedTrace = NewRelicUtils.createDistributedTrace();
    data.setTrace(distributedTrace);
    template.postForEntity("http://localhost:8082/data", data, Data.class);
  }
}
