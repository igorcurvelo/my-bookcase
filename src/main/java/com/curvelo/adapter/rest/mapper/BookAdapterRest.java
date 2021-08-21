package com.curvelo.adapter.rest.mapper;

import com.curvelo.api.dto.BookDTO;
import com.curvelo.core.domain.BookDomain;
import java.util.Optional;

public class BookAdapterRest {

  private BookAdapterRest() {}

  public static BookDTO toDTO(final BookDomain bookDomain) {
    return Optional.ofNullable(bookDomain)
        .map(entity ->
            BookDTO.builder()
                .id(entity.getId())
                .author(entity.getAuthor())
                .title(entity.getTitle())
                .isbn(entity.getIsbn())
                .numberOfPages(entity.getNumberOfPages()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }
}
