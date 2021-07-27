package com.curvelo;

import com.curvelo.core.domain.ReviewDomain;
import com.curvelo.core.domain.BookDomain;
import com.curvelo.core.domain.BookDomain.BookDomainBuilder;
import com.curvelo.core.domain.ReviewDomain.ReviewDomainBuilder;
import com.curvelo.core.domain.UserDomain;
import com.curvelo.core.domain.UserDomain.UserDomainBuilder;

public class ComposeDomain {

  private ComposeDomain() {}

  public static BookDomainBuilder createBook(final int bookId) {
    return BookDomain.builder()
        .id(bookId)
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit");
  }

  public static UserDomainBuilder createUser(final int userId) {
    return UserDomain.builder()
        .id(userId)
        .name("Igor");
  }

  public static ReviewDomainBuilder createReview(final int reviewId,
      final UserDomain userDomain, final BookDomain bookDomain) {
    return ReviewDomain.builder()
        .id(reviewId)
        .comment("excelente leitura")
        .score(4)
        .book(bookDomain)
        .user(userDomain);
  }

}
