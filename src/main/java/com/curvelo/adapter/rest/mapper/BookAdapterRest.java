package com.curvelo.adapter.rest.mapper;

import com.curvelo.api.dto.BookDTO;
import com.curvelo.core.domain.Author;
import com.curvelo.core.domain.BookDomain;
import com.curvelo.core.domain.Isbn;
import java.util.stream.Collectors;

public class BookAdapterRest {

  private BookAdapterRest() {}

  public static BookDTO toDTO(final BookDomain bookDomain) {
    return BookDTO.builder()
        .id(bookDomain.getId())
        .authors(bookDomain.getAuthors().stream()
            .map(Author::getName).collect(Collectors.toList()))
        .title(bookDomain.getTitle())
        .isbn(bookDomain.getIsbn().getValue())
        .numberOfPages(bookDomain.getNumberOfPages()).build();
  }

  public static BookDomain toDomain(final BookDTO dto) {
    return BookDomain.builder()
                .id(dto.getId())
                .authors(dto.getAuthors().stream().map(Author::from).collect(Collectors.toList()))
                .title(dto.getTitle())
                .isbn(Isbn.from(dto.getIsbn()))
                .numberOfPages(dto.getNumberOfPages()).build();
  }
}
