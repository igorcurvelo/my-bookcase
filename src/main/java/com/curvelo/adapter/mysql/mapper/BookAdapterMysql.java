package com.curvelo.adapter.mysql.mapper;

import com.curvelo.core.domain.BookDomain;
import com.curvelo.core.domain.BookDomain.BookDomainBuilder;
import com.curvelo.core.domain.Isbn;
import com.curvelo.database.model.BookModel;
import com.curvelo.database.model.ReviewModel;
import java.util.List;
import java.util.stream.Collectors;

public class BookAdapterMysql {

  private BookAdapterMysql() {}

  private static BookDomainBuilder builder(final BookModel bookModel) {
    return BookDomain.builder()
        .id(bookModel.getId())
        .authors(AuthorAdapterMysql.toDomain(bookModel.getAuthor()))
        .title(bookModel.getTitle())
        .isbn(Isbn.from(bookModel.getIsbn()))
        .numberOfPages(bookModel.getNumberOfPages());
  }

  public static BookDomain toDomain(final BookModel bookModel,
      final List<ReviewModel> reviews) {
    return builder(bookModel).reviews(reviews
                    .stream()
                    .map(ReviewAdapterMysql::toDomain)
                    .collect(Collectors.toList()))
                .build();
  }

  public static BookDomain toDomain(final BookModel bookModel) {
    return builder(bookModel).build();
  }

  public static BookModel toModel(final BookDomain bookDomain) {
    return BookModel.builder()
        .id(bookDomain.getId())
        .author(AuthorAdapterMysql.toModel(bookDomain.getAuthors()))
        .title(bookDomain.getTitle())
        .isbn(bookDomain.getIsbn().getValue())
        .numberOfPages(bookDomain.getNumberOfPages())
        .build();
  }
}
