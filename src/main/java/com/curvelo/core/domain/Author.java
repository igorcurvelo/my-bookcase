package com.curvelo.core.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class Author {

  private final String name;

  public static Author of(final String name) {
    return new Author(name);
  }

  private Author(final String name) {
    this.name = Objects.requireNonNull(name);
  }

}
