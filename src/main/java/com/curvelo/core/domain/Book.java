package com.curvelo.core.domain;

import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class Book {

  private final Integer id;
  private final String title;
  private final Isbn isbn;
  private final List<Author> authors;
  private final Integer numberOfPages;
  private final List<Review> reviews;

  public static Book of(
          final Integer id,
          final String title,
          final Isbn isbn,
          final List<Author> authors,
          final Integer numberOfPages,
          final List<Review> reviews) {
    return new Book(id, title, isbn, authors, numberOfPages, reviews);
  }

  private Book(
          final Integer id,
          final String title,
          final Isbn isbn,
          final List<Author> authors,
          final Integer numberOfPages,
          final List<Review> reviews) {
    this.id = id;
    this.title = title;
    this.isbn = isbn;
    this.authors = authors;
    this.numberOfPages = numberOfPages;
    this.reviews = Optional.ofNullable(reviews).orElse(Collections.emptyList());
  }

  public Double getAverageScore() {
    return this.reviews.stream()
        .map(Review::getScore)
        .mapToDouble(Integer::doubleValue)
        .average().orElse(0.0);
  }

  public List<UserReview> getComments() {
    return this.reviews.stream()
        .map(review ->
            UserReview.of(review.getComment(), review.getUser()))
        .collect(Collectors.toList());
  }

}
