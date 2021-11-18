package com.curvelo.core.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class TotalReviews {

  private final Double score;
  private final List<UserReview> comments;
  private final Book book;

  public static TotalReviews of(
      final Double score,
      final List<UserReview> comments,
      final Book book) {
    return new TotalReviews(score, comments, book);
  }

  private TotalReviews(
      final Double score,
      final List<UserReview> comments,
      final Book book) {
    this.score = score;
    this.comments = comments;
    this.book = book;
  }
}
