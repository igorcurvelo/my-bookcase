package com.curvelo;

import com.curvelo.core.domain.Author;
import com.curvelo.core.domain.Book;
import com.curvelo.core.domain.Isbn;
import com.curvelo.core.domain.Review;
import com.curvelo.core.domain.User;
import java.util.List;

public class ComposeDomain {

  private ComposeDomain() {}

  public static Book createBook(final int bookId) {
    return Book.of(
        bookId,
        "Hobbit",
        Isbn.of("9788533615540"),
        List.of(Author.of("J.R.R. Tolkien")),
            250, null);
  }

  public static Book createBook(final int bookId, final List<Review> reviews) {
    return Book.of(
            bookId,
            "Hobbit",
            Isbn.of("9788533615540"),
            List.of(Author.of("J.R.R. Tolkien")),
            250, reviews);
  }

  public static User createUser(final int userId) {
    return User.of(userId, "Igor");
  }

  public static Review createReview(
      final int reviewId,
      final User user) {
    return Review.of(
        reviewId,
        4,
        "excelente leitura",
        user
    );
  }

}
