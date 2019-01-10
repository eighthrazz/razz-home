package com.razz.service.garage;

import java.util.Objects;
import org.springframework.data.annotation.Id;

public class NexxGarageEmail {

  @Id
  private Long id;
  private String name;
  private String state;
  private Long date;

  public NexxGarageEmail(String name, String state, Long date) {
    this.name = name;
    this.state = state;
    this.date = date;
  }

  public Long getId() {
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

  public void setId(Long id) {
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
    NexxGarageEmail that = (NexxGarageEmail) o;
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
    return "NexxGarageEmail{"
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
