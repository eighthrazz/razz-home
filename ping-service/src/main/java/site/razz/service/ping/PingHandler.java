package site.razz.service.ping;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.razz.common.spring.entity.PingEvent;

@Component
public class PingHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(PingHandler.class);

  private final Map<String, InetAddress> inetAddressMap;

  @Value("${service.ping.timeoutMillis}")
  private int timeoutMillis;

  @Autowired private PingEventRepository repository;

  public PingHandler() {
    LOGGER.info("init: timeoutMillis={}", timeoutMillis);
    inetAddressMap = new HashMap<>();
  }

  public void run(String host) {
    try {
      // execute ping test
      final PingEvent pingTestEvent = ping(host);
      LOGGER.info("run: {}", pingTestEvent);

      // persist
      repository.save(pingTestEvent);
    } catch (Exception e) {
      final String msg = String.format("Unable to run ping test on host=%s", host);
      LOGGER.error(msg, e);
    }
  }

  PingEvent ping(String host) throws IOException {
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

  static PingEvent getPingTestEvent(String host, double millis) {
    return new PingEvent(host, millis, System.currentTimeMillis());
  }
}
