package com.curvelo.core.domain;

import lombok.Getter;

@Getter
public class UserReview {

  private final String comment;
  private final User user;

  public static UserReview of(String comment, User user) {
    return new UserReview(comment, user);
  }

  private UserReview(String comment, User user) {
    this.comment = comment;
    this.user = user;
  }
}
