package com.razz.service.ping;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PingTestHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(PingTestHandler.class);

  private final Map<String, InetAddress> inetAddressMap;

  @Value("${service.ping.timeoutMillis}")
  private int timeoutMillis;

  public PingTestHandler() {
    LOGGER.info("init: timeoutMillis={}", timeoutMillis);
    inetAddressMap = new HashMap<>();
  }

  public void run(String host) {
    try {
      // execute ping test
      final PingTestEvent pingTestEvent = ping(host);
      LOGGER.info("run: {}", pingTestEvent);

    } catch (Exception e) {
      final String msg = String.format("Unable to run ping test on host=%s", host);
      LOGGER.error(msg, e);
    }
  }

  PingTestEvent ping(String host) throws IOException {
    final InetAddress inetAddress = getInetAddress(host);
    final long startNanos = System.nanoTime();
    inetAddress.isReachable(timeoutMillis);
    final long stopNanos = System.nanoTime();
    final double elapsedMillis = (stopNanos - startNanos) / 1000000.0;
    return getPingTestEvent(host, elapsedMillis);
  }

  InetAddress getInetAddress(String host) throws UnknownHostException {
    if (!inetAddressMap.containsKey(host)) {
      LOGGER.info("Initializing InetAddress {}", host);
      inetAddressMap.put(host, InetAddress.getByName(host));
    }
    return inetAddressMap.get(host);
  }

  static PingTestEvent getPingTestEvent(String host, double millis) {
    return new PingTestEvent(host, millis, System.currentTimeMillis());
  }
}
