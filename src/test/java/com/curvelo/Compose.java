package com.curvelo;

import com.curvelo.domain.model.Avaliation;
import com.curvelo.domain.model.Avaliation.AvaliationBuilder;
import com.curvelo.domain.model.Book;
import com.curvelo.domain.model.Book.BookBuilder;
import com.curvelo.domain.model.User;
import com.curvelo.domain.model.User.UserBuilder;

public class Compose {

  private Compose() {}

  public static BookBuilder createBook(final int bookId) {
    return Book.builder()
        .id(bookId)
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit");
  }

  public static UserBuilder createUser(final int userId) {
    return User.builder()
        .id(99)
        .name("Igor");
  }

  public static AvaliationBuilder createAvaliation(final int avaliationId,
      final User user, final Book book) {
    return Avaliation.builder()
        .id(1)
        .comment("excelente leitura")
        .score(4)
        .book(book)
        .user(user);
  }

}
