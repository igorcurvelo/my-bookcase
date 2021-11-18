package com.curvelo.core.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class Review {

  private final Integer id;
  private final Integer score;
  private final String comment;
  private final User user;

  public static Review of(
      final Integer id,
      final Integer score,
      final String comment,
      final User user) {
    return new Review(id, score, comment, user);
  }

  private Review(
      final Integer id,
      final Integer score,
      final String comment,
      final User user) {
    this.id = id;
    this.score = Objects.requireNonNull(score);
    this.comment = comment;
    this.user = Objects.requireNonNull(user);
  }
}
