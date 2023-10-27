package com.example.app1;

import com.newrelic.api.agent.DestinationType;
import com.newrelic.api.agent.MessageProduceParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.TransactionNamePriority;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageSender {

  Executor executor = Executors.newSingleThreadExecutor();
  RestTemplate template = new RestTemplateBuilder().build();

  @Trace
  public void send(Data data) {
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
    executor.execute(() -> {
      ResponseEntity<Data> dataResponseEntity =
          template.postForEntity("http://localhost:8080/data1", data, Data.class);
    });
  }
}
