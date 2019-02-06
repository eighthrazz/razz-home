package com.razz.service.ping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class PingTestScheduler {

  @Value("${service.ping.host.wan}")
  private String hostWan;

  @Value("${service.ping.host.lan}")
  private String hostLan;

  @Autowired private PingTestHandler pingTestHandler;

  @Scheduled(fixedDelay = 1000)
  void runWan() {
    pingTestHandler.run(hostWan);
  }

  @Scheduled(fixedDelay = 1000)
  void runLan() {
    pingTestHandler.run(hostLan);
  }
}
