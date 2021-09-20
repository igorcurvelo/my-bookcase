package com.curvelo.core.domain;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BookDomain {

  private final Integer id;
  private final String title;
//  private final Isbn isbn;
  private final String isbn;
  private final String author;
  private final Integer numberOfPages;
  private final List<ReviewDomain> reviews;

  @Builder
  public BookDomain(Integer id, String title, String isbn, String author,
      Integer numberOfPages, List<ReviewDomain> reviews) {
    this.id = id;
    this.title = title;
    this.isbn = isbn;
    this.author = author;
    this.numberOfPages = numberOfPages;
    this.reviews = Optional.ofNullable(reviews).orElse(Collections.emptyList());
  }

  public Double getAverageScore() {
    return this.reviews.stream()
        .map(ReviewDomain::getScore)
        .mapToDouble(Integer::doubleValue)
        .average().orElse(0.0);
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
