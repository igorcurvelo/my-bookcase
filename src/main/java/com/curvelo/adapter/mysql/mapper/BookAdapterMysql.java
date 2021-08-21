package com.curvelo.adapter.mysql.mapper;

import com.curvelo.core.domain.BookDomain;
import com.curvelo.database.model.BookModel;
import java.util.Optional;

public class BookAdapterMysql {

  private BookAdapterMysql() {}

  public static BookDomain toDomain(final BookModel bookModel) {
    return Optional.ofNullable(bookModel)
        .map(entity ->
            BookDomain.builder()
                .id(entity.getId())
                .author(entity.getAuthor())
                .title(entity.getTitle())
                .isbn(entity.getIsbn())
                .numberOfPages(entity.getNumberOfPages()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

}
