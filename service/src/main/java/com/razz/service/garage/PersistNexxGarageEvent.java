package com.razz.service.garage;

import com.razz.util.EmailFetcher;
import com.razz.util.TextUtil;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PersistNexxGarageEvent {

  private static final Logger LOGGER = LoggerFactory.getLogger(PersistNexxGarageEvent.class);

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

  @Autowired private NexxGarageEventRepository repository;

  public PersistNexxGarageEvent() {
    emailFetcher = null;
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

  @Scheduled(fixedDelay = 30000) // TODO
  public void run() {
    LOGGER.info("Running scheduled execution of {}...", getClass().getSimpleName());

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

    // persist
    try {
      for(NexxGarageEvent ngEvent : parseList) {
        persist(ngEvent);
      }
    } catch (Exception e) {
      LOGGER.error("Unable to persist email. ", e);
      return;
    }
  }

  List<String> fetch() throws Exception {
    final boolean markAsRead = false;
    final List<String> messageList = getEmailFetcher().fetchMessages(emailFolder, markAsRead);
    return messageList;
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

  void persist(NexxGarageEvent ngEvent) {
    //if (!repository.exists(ngEvent.getDate())) {
      //LOGGER.info("{} does not exist. ", ngEvent);
    //}
    repository.save(ngEvent);
  }
}
