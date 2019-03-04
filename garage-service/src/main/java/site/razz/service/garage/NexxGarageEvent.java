package site.razz.service.garage;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "NexxGarageEvent")
public class NexxGarageEvent {

  @Id private String id;
  private String name;
  private String state;
  private Long date;

  public NexxGarageEvent() {
    this(null, null, 0L);
  }

  public NexxGarageEvent(String name, String state, Long date) {
    this.name = name;
    this.state = state;
    this.date = date;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getState() {
    return state;
  }

  public Long getDate() {
    return date;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setState(String state) {
    this.state = state;
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
    NexxGarageEvent that = (NexxGarageEvent) o;
    return Objects.equals(id, that.id)
        && Objects.equals(name, that.name)
        && Objects.equals(state, that.state)
        && Objects.equals(date, that.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, state, date);
  }

  @Override
  public String toString() {
    return "NexxGarageEvent{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", state='"
        + state
        + '\''
        + ", date="
        + date
        + '}';
  }
}
