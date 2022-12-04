package com.curvelo.adapter.mysql.mapper;

import com.curvelo.core.domain.Book;
import com.curvelo.core.domain.Isbn;
import com.curvelo.database.model.BookModel;
import com.curvelo.database.model.ReviewModel;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class BookAdapterMysql {

  private final AuthorAdapterMysql authorAdapterMysql;
  private final ReviewAdapterMysql reviewAdapterMysql;

  public BookAdapterMysql(
      final AuthorAdapterMysql authorAdapterMysql,
      final ReviewAdapterMysql reviewAdapterMysql) {
    this.authorAdapterMysql = authorAdapterMysql;
    this.reviewAdapterMysql = reviewAdapterMysql;
  }

  public Book toDomain(final BookModel bookModel) {
    return Book.of(
            bookModel.getId(),
            bookModel.getTitle(),
            Isbn.of(bookModel.getIsbn()),
            authorAdapterMysql.toDomain(bookModel.getAuthor()),
            bookModel.getNumberOfPages(), null);
  }

  public Book toDomain(final BookModel bookModel,
                              final List<ReviewModel> reviews) {
    return Book.of(
            bookModel.getId(),
            bookModel.getTitle(),
            Isbn.of(bookModel.getIsbn()),
            authorAdapterMysql.toDomain(bookModel.getAuthor()),
            bookModel.getNumberOfPages(),
            reviews.stream().map(reviewAdapterMysql::toDomain).collect(Collectors.toList()));
  }

  public BookModel toModel(final Book book) {
    return BookModel.builder()
        .id(book.getId())
        .author(authorAdapterMysql.toModel(book.getAuthors()))
        .title(book.getTitle())
        .isbn(book.getIsbn().getValue())
        .numberOfPages(book.getNumberOfPages())
        .build();
  }
}
