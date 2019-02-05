package com.razz.service.garage;

import com.razz.util.EmailFetcher;
import com.razz.util.TextUtil;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NexxGarageHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(NexxGarageHandler.class);

  static final String REGEX = "(.*) has been (.*) on (.*) at (.*)";
  static final String DTG_FORMAT = "MMMMM d, yyyy hh:mma";

  private EmailFetcher emailFetcher;

  @Value("${service.garage.email.host}")
  private String emailHost;

  @Value("${service.garage.email.port}")
  private int emailPort;

  @Value("${service.garage.email.username}")
  private String emailUsername;

  @Value("${service.garage.email.password}")
  private String emailPassword;

  @Value("${service.garage.email.folder}")
  private String emailFolder;

  @Value("${service.garage.email.delayMillis}")
  private long emailDelayMillis;

  @Value("${service.garage.email.markAsRead}")
  private boolean markAsRead;

  @Autowired private NexxGarageEventRepository repository;

  public NexxGarageHandler() {
    emailFetcher = null;
  }

  void test() {
    LOGGER.info("emailHost:{}", emailHost);
  }

  /** run. */
  public void run() {
    // fetch
    final List<String> fetchList = new ArrayList<>();
    try {
      fetchList.addAll(fetch());
    } catch (Exception e) {
      LOGGER.error("Unable to fetch emails. ", e);
      return;
    }

    // parse
    final List<NexxGarageEvent> parseList = new ArrayList<>();
    try {
      parseList.addAll(parse(fetchList));
    } catch (Exception e) {
      LOGGER.error("Unable to parse emails. ", e);
      return;
    }

    // persist new events only
    try {
      for (NexxGarageEvent ngEvent : parseList) {
        persistNewEvent(ngEvent);
      }
    } catch (Exception e) {
      LOGGER.error("Unable to persist email. ", e);
      return;
    }
  }

  List<String> fetch() throws Exception {
    final List<String> messageList = getEmailFetcher().fetchMessages(emailFolder, markAsRead);
    return messageList;
  }

  private EmailFetcher getEmailFetcher() {
    if (emailFetcher == null) {
      LOGGER.info(
          "{} : emailHost={}, emailPort={}, emailUsername={}, emailPassword=<hidden>",
          getClass().getSimpleName(),
          emailHost,
          emailPort,
          emailUsername);
      emailFetcher = new EmailFetcher(emailHost, emailPort, emailUsername, emailPassword);
    }
    return emailFetcher;
  }

  List<NexxGarageEvent> parse(List<String> fetchList) throws Exception {
    final List<NexxGarageEvent> parseList = new ArrayList<>();
    for (String fetch : fetchList) {
      final NexxGarageEvent ngEvent = parse(fetch);
      parseList.add(ngEvent);
    }
    return parseList;
  }

  static NexxGarageEvent parse(String message) throws Exception {
    try {
      final String name = TextUtil.find(REGEX, 1, message, "").trim();
      final String state = TextUtil.find(REGEX, 2, message, "").trim();
      final String dateStr = TextUtil.find(REGEX, 3, message, "").trim();
      final String timeStr = TextUtil.find(REGEX, 4, message, "").trim();
      final String dtg = dateStr.concat(" ").concat(timeStr);
      final Long date = TextUtil.dateToLong(dtg, DTG_FORMAT);
      final NexxGarageEvent ngEmail = new NexxGarageEvent(name, state, date);
      return ngEmail;
    } catch (Exception e) {
      final String error = String.format("Unable to parse message: %s", message);
      throw new Exception(error, e);
    }
  }

  void persistNewEvent(NexxGarageEvent ngEvent) {
    if (repository.findByDate(ngEvent.getDate()).isEmpty()) {
      LOGGER.info("Saving event: {}", ngEvent);
      repository.save(ngEvent);
    } else {
      LOGGER.info("Event already exists. Ignoring event: {}", ngEvent);
    }
  }
}
