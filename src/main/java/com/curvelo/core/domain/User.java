package com.curvelo.core.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class User {

  private final Integer id;
  private final String name;

  public static User of(
      final Integer id,
      final String name) {
    return new User(id, name);
  }

  private User(
      final Integer id,
      final String name) {
    this.id = id;
    this.name = Objects.requireNonNull(name);
  }
}
