package com.curvelo.core.domain;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookDomain {

  private final Integer id;
  private final String title;
  private final String isbn;
  private final String author;
  private final Integer numberOfPages;
  private final List<ReviewDomain> reviews;

  public Double getAverageScore() {
    return this.reviews.stream()
        .map(ReviewDomain::getScore)
        .mapToDouble(Integer::doubleValue)
        .average()
        .orElse(0.0);
  }

  public List<UserReviewDomain> getComments() {
    return this.reviews.stream()
        .map(review ->
            UserReviewDomain.builder()
                .comment(review.getComment())
                .user(review.getUser())
                .build())
        .collect(Collectors.toList());
  }

}
