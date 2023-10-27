package com.example.app3;

import com.newrelic.api.agent.Agent;
import com.newrelic.api.agent.ConcurrentHashMapHeaders;
import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.TransportType;
import java.util.List;
import java.util.Map;

public class NewRelicUtils {
  public static void continueDistributedTransaction(Map<String, List<String>> traceId) {
    ConcurrentHashMapHeaders headers1 =
        ConcurrentHashMapHeaders.buildFromMap(HeaderType.MESSAGE, traceId);
    NewRelic.getAgent().getTransaction()
        .acceptDistributedTraceHeaders(TransportType.Kafka, headers1);
  }

  public static Map<String, List<String>> createDistributedTrace() {
    Agent agent = NewRelic.getAgent();
    ConcurrentHashMapHeaders build = ConcurrentHashMapHeaders.build(HeaderType.MESSAGE);
    agent.getTransaction().insertDistributedTraceHeaders(build);
    return build.getMapCopy();
  }
}

