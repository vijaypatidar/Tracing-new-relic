package com.example.demo;

import com.example.NewRelicUtils;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.PubsubMessage;
import com.newrelic.api.agent.Trace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DummyReceiver implements MessageReceiver {
  @Override
  @Trace(dispatcher = true)
  public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
    NewRelicUtils.continueDistributedTransaction(message.getAttributesMap());
    consumer.ack();
    log.error("Message:{} , Headers:{}", message.getData(), message.getAttributesMap());
  }
}
