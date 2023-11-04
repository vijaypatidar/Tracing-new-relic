package com.example;

import com.newrelic.api.agent.Agent;
import com.newrelic.api.agent.ConcurrentHashMapHeaders;
import com.newrelic.api.agent.DestinationType;
import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.MessageConsumeParameters;
import com.newrelic.api.agent.MessageProduceParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.TransportType;
import java.util.HashMap;
import java.util.Map;

public class NewRelicUtils {
  public static void continueDistributedTransaction(Map<String, String> traceId) {

    ConcurrentHashMapHeaders headers1 =
        ConcurrentHashMapHeaders.buildFromFlatMap(HeaderType.MESSAGE, traceId);
    NewRelic.getAgent().getTransaction()
        .acceptDistributedTraceHeaders(TransportType.Other, headers1);

    MessageConsumeParameters consumeParameters = MessageConsumeParameters
        .library("GCP-PubSub")
        .destinationType(DestinationType.NAMED_TOPIC)
        .destinationName("BiPubSub")
        .inboundHeaders(headers1)
        .build();
    NewRelic.getAgent().getTracedMethod().reportAsExternal(consumeParameters);
  }

  public static Map<String, String> createDistributedTrace() {
    Agent agent = NewRelic.getAgent();
    ConcurrentHashMapHeaders build = ConcurrentHashMapHeaders.build(HeaderType.MESSAGE);
    agent.getTransaction().insertDistributedTraceHeaders(build);

    MessageProduceParameters produceParameters = MessageProduceParameters
        .library("GCP-PubSub")
        .destinationType(DestinationType.NAMED_TOPIC)
        .destinationName("BiPubSub")
        .outboundHeaders(build)
        .build();

    NewRelic.getAgent().getTracedMethod().reportAsExternal(produceParameters);

    Map<String, String> trace = new HashMap<>();
    build.getHeaderNames().forEach(header -> {
      trace.put(header, build.getHeader(header));
    });
    return trace;
  }
}



