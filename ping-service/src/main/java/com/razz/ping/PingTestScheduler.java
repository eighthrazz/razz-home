package com.razz.ping;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class PingTestScheduler {

  private static final Logger LOGGER = LoggerFactory.getLogger(PingTestScheduler.class);

  @Value("${service.ping.host.wan}")
  private String hostWan;

  @Value("${service.ping.host.lan}")
  private String hostLan;

  @Value("${service.ping.event.expiredMillis}")
  private long maxEventMillis;

  @Autowired private PingTestHandler pingTestHandler;

  @Autowired private PingTestEventRepository repository;

  @Scheduled(fixedDelay = 1000)
  void runWan() {
    pingTestHandler.run(hostWan);
  }

  @Scheduled(fixedDelay = 1000)
  void runLan() {
    pingTestHandler.run(hostLan);
  }

  @Scheduled(initialDelay = 5000, fixedDelay = 60_000_000)
  void deleteOldEvents() {
    final long expireMillis = System.currentTimeMillis() - maxEventMillis;
    final List<PingTestEvent> pingTestEventList = repository.findByDateLessThan(expireMillis);
    LOGGER.info("deleteOldEvents: pingTestEventList.size={}", pingTestEventList.size());
    repository.deleteAll(pingTestEventList);
  }
}
