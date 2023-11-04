package com.example.demo;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.InstantiatingExecutorProvider;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DummySubscription {

  private final DummyReceiver dummyReceiver;
  private final CredentialsProvider credentials;
  @Value("${spring.cloud.gcp.credentials.location}")
  String path;

  @PostConstruct
  public void afterStartUp() throws IOException {
    var exec = InstantiatingExecutorProvider.newBuilder().setExecutorThreadCount(2).build();
    var projectName = ProjectSubscriptionName.of("crack-celerity-357721", "dummy-sub");


    // Set the `CredentialsProvider` globally for Google Cloud client librarie

    Subscriber subscriber = Subscriber.newBuilder(projectName, dummyReceiver)
        .setExecutorProvider(exec)
        .setParallelPullCount(2)
        .setCredentialsProvider(credentials)
        .build();
    subscriber.startAsync().awaitRunning();
  }

}
