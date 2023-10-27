package com.example.app1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newrelic.api.agent.DestinationType;
import com.newrelic.api.agent.MessageProduceParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.TransactionNamePriority;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
    Map<String, List<String>> distributedTrace = NewRelicUtils.createDistributedTrace();
    data.setTraceId(distributedTrace);

    TracedMethod method = NewRelic.getAgent().getTracedMethod();
    method.reportAsExternal(MessageProduceParameters
        .library("GCP-PubSub")
        .destinationType(DestinationType.NAMED_TOPIC)
        .destinationName("BiPubSub")
        .outboundHeaders(null).build()
    );
    NewRelic.getAgent().getTransaction().setTransactionName(
        TransactionNamePriority.CUSTOM_LOW,
        false,
        "GCP-PubSub",
        "GCP-PubSub",
        "Publish",
        "BiPubSub"
    );
//    executor.execute(() -> {
//      ResponseEntity<Data> dataResponseEntity =
//          template.postForEntity("http://localhost:8080/data1", data, Data.class);
//    });

    File file = new File(parent, UUID.randomUUID().toString() + ".txt");
    String s = mapper.writeValueAsString(data);
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    fileOutputStream.write(s.getBytes());
    fileOutputStream.close();
    System.err.println("Logged:"+s);
  }
}
