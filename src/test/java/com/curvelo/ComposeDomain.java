package com.curvelo;

import com.curvelo.core.domain.Author;
import com.curvelo.core.domain.Book;
import com.curvelo.core.domain.Isbn;
import com.curvelo.core.domain.ReviewDomain;
import com.curvelo.core.domain.ReviewDomain.ReviewDomainBuilder;
import com.curvelo.core.domain.UserDomain;
import com.curvelo.core.domain.UserDomain.UserDomainBuilder;
import java.util.List;

public class ComposeDomain {

  private ComposeDomain() {}

  public static Book createBook(final int bookId) {
    return Book.of(
        bookId,
        "Hobbit",
        Isbn.from("9788533615540"),
        List.of(Author.of("J.R.R. Tolkien")),
            250, null);
  }

  public static Book createBook(final int bookId, final List<ReviewDomain> reviews) {
    return Book.of(
            bookId,
            "Hobbit",
            Isbn.from("9788533615540"),
            List.of(Author.of("J.R.R. Tolkien")),
            250, reviews);
  }

  public static UserDomainBuilder createUser(final int userId) {
    return UserDomain.builder()
        .id(userId)
        .name("Igor");
  }

  public static ReviewDomainBuilder createReview(final int reviewId,
      final UserDomain userDomain) {
    return ReviewDomain.builder()
        .id(reviewId)
        .comment("excelente leitura")
        .score(4)
        .user(userDomain);
  }

}
