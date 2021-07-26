package com.curvelo.adapter.mysql.mapper;

import com.curvelo.core.domain.BookDomain;
import com.curvelo.database.model.BookModel;
import java.util.Optional;

public class BookAdapterMysql {

  private BookAdapterMysql() {}

  public static BookModel toEntity(final BookDomain bookDomain) {
    return Optional.ofNullable(bookDomain)
        .map(dto ->
            BookModel.builder()
                .id(dto.getId())
                .author(dto.getAuthor())
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .numberOfPages(bookDomain.getNumberOfPages()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

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
