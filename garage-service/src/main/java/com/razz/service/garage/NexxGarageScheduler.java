package com.razz.service.garage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class NexxGarageScheduler {

  private static final Logger LOGGER = LoggerFactory.getLogger(NexxGarageScheduler.class);

  @Autowired
  private NexxGarageHandler nexxGarageEventHandler;

  @Scheduled(fixedDelay = 30000) // TODO
  void run() {
    LOGGER.info("Running scheduled execution of {}...", getClass().getSimpleName());
    nexxGarageEventHandler.run();
  }
}
