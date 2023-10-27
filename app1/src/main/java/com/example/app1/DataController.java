package com.example.app1;

import com.newrelic.api.agent.ConcurrentHashMapHeaders;
import com.newrelic.api.agent.DestinationType;
import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.MessageProduceParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.TracedMethod;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DataController {

  RestTemplate template = new RestTemplateBuilder().build();
  Logger logger = LoggerFactory.getLogger(DataController.class);
  Executor executor = Executors.newSingleThreadExecutor();

  @PostMapping("/data")
  Data test(@RequestBody Data data) {
    template.postForEntity("http://localhost:8080/data", data, Data.class);
    return data;
  }

  @PostMapping("/data1")
  Data test1(@RequestBody Data data) {
    Data data1 = new Data();
    data1.setTraceId(NewRelicUtils.createDistributedTrace());

    TracedMethod method = NewRelic.getAgent().getTracedMethod();
    method.reportAsExternal(MessageProduceParameters.library(
            "PubSub").destinationType(
            DestinationType.NAMED_TOPIC).destinationName("BiPubSub")
        .outboundHeaders(ConcurrentHashMapHeaders.buildFromMap(HeaderType.MESSAGE,
            NewRelicUtils.createDistributedTrace())).build()
    );


//    executor.execute(() -> {
//      ResponseEntity<Data> dataResponseEntity =
//          template.postForEntity("http://localhost:8080/data1", data1, Data.class);
//    });
    return data1;
  }
}
