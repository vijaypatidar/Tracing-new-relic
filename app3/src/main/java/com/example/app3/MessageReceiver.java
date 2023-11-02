package com.example.app3;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.Trace;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageReceiver {
  RestTemplate template = new RestTemplateBuilder().build();

  @Trace(dispatcher = true)
  public void run(Data data) {
    Segment processingMessage =
        NewRelic.getAgent().getTransaction().startSegment("ProcessingMessage");
    try {
      NewRelicUtils.continueDistributedTransaction(data.getTrace());
      NewRelic.setTransactionName("Consumer", "ProcessSubMessage1");
      processingMessage.setMetricName("ProcessingMessage/time");
      template.postForEntity("http://localhost:8081/data", data, Data.class);
    } finally {
      processingMessage.end();
    }
  }
}
