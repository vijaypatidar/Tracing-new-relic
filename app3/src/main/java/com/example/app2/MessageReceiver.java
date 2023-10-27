package com.example.app2;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import org.springframework.stereotype.Service;

@Service
public class MessageReceiver {
  @Trace(dispatcher = true)
  public void run(Data data) {
    NewRelicUtils.continueDistributedTransaction(data.getTraceId());
    NewRelic.setTransactionName("Consumer", "ProcessSubMessage1");
  }
}
