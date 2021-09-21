package com.curvelo.adapter.mysql.mapper;

import com.curvelo.core.domain.BookDomain;
import com.curvelo.core.domain.Isbn;
import com.curvelo.database.model.BookModel;
import com.curvelo.database.model.ReviewModel;
import java.util.List;
import java.util.stream.Collectors;

public class BookAdapterMysql {

  private BookAdapterMysql() {}

  public static BookDomain toDomain(final BookModel bookModel,
      final List<ReviewModel> reviews) {
    return BookDomain.builder()
                .id(bookModel.getId())
                .authors(AuthorAdapterMysql.toDomain(bookModel.getAuthor()))
                .title(bookModel.getTitle())
                .isbn(Isbn.from(bookModel.getIsbn()))
                .numberOfPages(bookModel.getNumberOfPages())
                .reviews(reviews
                    .stream()
                    .map(ReviewAdapterMysql::toDomain)
                    .collect(Collectors.toList()))
                .build();
  }
}
