package site.razz.common.spring.entity;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "PingEvent")
public class PingEvent {

  @Id private String id;
  private String host;
  private Double millis;
  private Long date;

  public PingEvent() {
    this(null, 0D, 0L);
  }

  public PingEvent(String host, Double millis, Long date) {
    this.host = host;
    this.millis = millis;
    this.date = date;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public Double getMillis() {
    return millis;
  }

  public void setMillis(Double millis) {
    this.millis = millis;
  }

  public Long getDate() {
    return date;
  }

  public void setDate(Long date) {
    this.date = date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PingEvent that = (PingEvent) o;
    return Objects.equals(id, that.id)
        && Objects.equals(host, that.host)
        && Objects.equals(millis, that.millis)
        && Objects.equals(date, that.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, host, millis, date);
  }

  @Override
  public String toString() {
    return "PingEvent{"
        + "id='"
        + id
        + '\''
        + ", host='"
        + host
        + '\''
        + ", millis="
        + millis
        + ", date="
        + date
        + '}';
  }
}
