package com.razz.service.garage;

import com.razz.util.EmailFetcher;
import com.razz.util.TextUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PersistNexxGarageEmail {

  private static final Logger LOGGER = LoggerFactory.getLogger(PersistNexxGarageEmail.class);

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

  public PersistNexxGarageEmail() {
    emailFetcher = null;
  }

  private EmailFetcher getEmailFetcher() {
    if (emailFetcher == null) {
      LOGGER.info(
          "{} : emailHost={}, emailPort={}, emailUsername={}, emailPassword=***",
          getClass().getSimpleName(),
          emailHost,
          emailPort,
          emailUsername);
      emailFetcher = new EmailFetcher(emailHost, emailPort, emailUsername, emailPassword);
    }
    return emailFetcher;
  }

  @Scheduled(fixedDelay = 30000) // TODO
  public void fetch() {
    try {
      final boolean markAsRead = false;
      final List<String> messageList = getEmailFetcher().fetchMessages(emailFolder, markAsRead);
      parse(messageList);
    } catch (Exception e) {
      final String err = "Unable to read email.";
      LOGGER.error(err, e);
    }
  }

  static void parse(List<String> messageList) {
    for (String msg : messageList) {
      try {
        final NexxGarageEmail ngEmail = parse(msg);
        persist(ngEmail);
      } catch (Exception e) {
        final String err = "Unable to parse email.";
        LOGGER.error(err, e);
      }
    }
  }

  static NexxGarageEmail parse(String message) throws Exception {
    try {
      final String name = TextUtil.find(REGEX, 1, message, "").trim();
      final String state = TextUtil.find(REGEX, 2, message, "").trim();
      final String dateStr = TextUtil.find(REGEX, 3, message, "").trim();
      final String timeStr = TextUtil.find(REGEX, 4, message, "").trim();
      final String dtg = dateStr.concat(" ").concat(timeStr);
      final Long date = TextUtil.dateToLong(dtg, DTG_FORMAT);
      final NexxGarageEmail ngEmail = new NexxGarageEmail(name, state, date);
      return ngEmail;
    } catch (Exception e) {
      final String msg = String.format("Unable to parse message: %s", message);
      throw new Exception(message, e);
    }
  }

  static void persist(NexxGarageEmail ngEmail) {
    System.out.println(ngEmail); // TODO
  }
}
