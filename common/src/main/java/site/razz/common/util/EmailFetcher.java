package site.razz.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailFetcher {

  public enum Key {
    host("host"),
    port("port"),
    username("username"),
    password("password");

    private final String key;

    Key(String key) {
      this.key = key;
    }

    int toInt(Properties properties) {
      return Integer.parseInt(toString(properties));
    }

    String toString(Properties properties) {
      return properties.get(key).toString();
    }

    static Properties toProps(String host, int port, String username, String password) {
      final Properties props = new Properties();
      props.put(Key.host.key, host);
      props.put(Key.port.key, port);
      props.put(Key.username.key, username);
      props.put(Key.password.key, password);
      return props;
    }
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(EmailFetcher.class);

  private final Properties properties;

  public EmailFetcher(String host, int port, String username, String password) {
    this(Key.toProps(host, port, username, password));
  }

  public EmailFetcher(Properties properties) {
    this.properties = properties;
  }
  
  public List<String> fetchMessages(String folder, boolean markAsRead) throws Exception {
    LOGGER.info("fetchMessages: folder={}, markAsRead={}", folder, markAsRead);
    final String host = Key.host.toString(properties);
    final int port = Key.port.toInt(properties);
    final String username = Key.username.toString(properties);
    final String password = Key.password.toString(properties);
    final Message[] messages = fetchMessages(host, port, username, password, folder, markAsRead);
    LOGGER.info(String.format("fetched %s messages from folder %s", messages.length, folder));

    LOGGER.info("reading messages...");
    final List<String> messageList = new ArrayList<>();
    for (Message m : messages) {
      final String text = TextUtil.toString(m.getInputStream());
      messageList.add(text);
    }
    LOGGER.info("...done reading messages");
    return messageList;
  }

  static Message[] fetchMessages(
      String host, int port, String username, String password, String folder, boolean markAsRead)
      throws Exception {
    final Properties properties = new Properties();
    properties.put("mail.store.protocol", "imaps");

    final Session emailSession = Session.getDefaultInstance(properties);
    final Store store = emailSession.getStore();
    // connect
    store.connect(host, port, username, password);

    final Folder emailFolder = store.getFolder(folder);
    // use READ_ONLY if you don't wish the messages
    // to be marked as read after retrieving its content
    emailFolder.open(markAsRead ? Folder.READ_WRITE : Folder.READ_ONLY);

    // search for all "unseen" messages
    final Flags seen = new Flags(Flags.Flag.SEEN);
    final boolean read = false;
    final FlagTerm unseenFlagTerm = new FlagTerm(seen, read);
    return emailFolder.search(unseenFlagTerm);
  }
}
