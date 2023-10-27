package com.example.app3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newrelic.api.agent.Trace;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerUtils {
  @Autowired
  ObjectMapper mapper;
  @Autowired
  MessageReceiver receiver;

  Logger logger = LoggerFactory.getLogger(ConsumerUtils.class);

  @PostConstruct
  public void run() throws Exception {
      try {
        File parent = new File("/Users/vijaypatidar/IdeaProjects/Tracing/tmp");
        String[] list = parent.list();
        for (String name : list) {
          File file = new File(parent, name);
          logger.error(name);
          FileInputStream fis = new FileInputStream(file);
          Data data = mapper.createParser(fis).readValueAs(Data.class);
          file.delete();
          receiver.run(data);
        }
      } catch (Exception e) {
        logger.error("File", e);
        Thread.sleep(200);
      }
  }

}
