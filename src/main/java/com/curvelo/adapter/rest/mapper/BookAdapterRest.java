package com.curvelo.adapter.rest.mapper;

import com.curvelo.api.dto.BookDTO;
import com.curvelo.core.domain.Author;
import com.curvelo.core.domain.BookDomain;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookAdapterRest {

  private BookAdapterRest() {}

  public static BookDTO toDTO(final BookDomain bookDomain) {
    return Optional.ofNullable(bookDomain)
        .map(entity ->
            BookDTO.builder()
                .id(entity.getId())
                .authors(entity.getAuthors().stream()
                    .map(Author::getName).collect(Collectors.toList()))
                .title(entity.getTitle())
                .isbn(entity.getIsbn().getValue())
                .numberOfPages(entity.getNumberOfPages()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }
}
