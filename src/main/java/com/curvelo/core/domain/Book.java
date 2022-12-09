package com.curvelo.core.domain;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;

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
    this.title = Objects.requireNonNull(title);
    this.isbn = Objects.requireNonNull(isbn);
    this.authors = verifyRequireList(authors);
    this.numberOfPages = Objects.requireNonNull(numberOfPages);
    this.reviews = Optional.ofNullable(reviews).orElse(Collections.emptyList());
  }

  private List<Author> verifyRequireList(List<Author> authors) {
    if (Objects.nonNull(authors) && !authors.isEmpty()) {
      return authors;
    }
    throw new NullPointerException();
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
