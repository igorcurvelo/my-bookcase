package com.curvelo.adapter.mysql.mapper;

import com.curvelo.core.domain.Book;
import com.curvelo.core.domain.Isbn;
import com.curvelo.database.model.BookModel;
import com.curvelo.database.model.ReviewModel;

import java.util.List;
import java.util.stream.Collectors;

public class BookAdapterMysql {

  private BookAdapterMysql() {}

  public static Book toDomain(final BookModel bookModel) {
    return Book.of(
            bookModel.getId(),
            bookModel.getTitle(),
            Isbn.of(bookModel.getIsbn()),
            AuthorAdapterMysql.toDomain(bookModel.getAuthor()),
            bookModel.getNumberOfPages(), null);
  }

  public static Book toDomain(final BookModel bookModel,
                              final List<ReviewModel> reviews) {
    return Book.of(
            bookModel.getId(),
            bookModel.getTitle(),
            Isbn.of(bookModel.getIsbn()),
            AuthorAdapterMysql.toDomain(bookModel.getAuthor()),
            bookModel.getNumberOfPages(),
            reviews.stream().map(ReviewAdapterMysql::toDomain).collect(Collectors.toList()));
  }

  public static BookModel toModel(final Book book) {
    return BookModel.builder()
        .id(book.getId())
        .author(AuthorAdapterMysql.toModel(book.getAuthors()))
        .title(book.getTitle())
        .isbn(book.getIsbn().getValue())
        .numberOfPages(book.getNumberOfPages())
        .build();
  }
}
