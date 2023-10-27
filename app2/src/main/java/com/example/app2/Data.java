package com.example.app2;

import java.util.List;
import java.util.Map;

public class Data {
  String username;
  Map<String, List<String>> traceId;


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Map<String, List<String>> getTraceId() {
    return traceId;
  }

  public void setTraceId(Map<String, List<String>> traceId) {
    this.traceId = traceId;
  }
}