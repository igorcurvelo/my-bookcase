package com.curvelo.adapter.rest.mapper;

import com.curvelo.api.dto.BookDTO;
import com.curvelo.core.domain.Author;
import com.curvelo.core.domain.Book;
import com.curvelo.core.domain.Isbn;

import java.util.stream.Collectors;

public class BookAdapterRest {

  private BookAdapterRest() {}

  public static BookDTO toDTO(final Book book) {
    return BookDTO.builder()
        .id(book.getId())
        .authors(book.getAuthors().stream()
            .map(Author::getName).collect(Collectors.toList()))
        .title(book.getTitle())
        .isbn(book.getIsbn().getValue())
        .numberOfPages(book.getNumberOfPages()).build();
  }

  public static Book toDomain(final BookDTO dto) {
    return Book.of(
            dto.getId(),
            dto.getTitle(),
            Isbn.of(dto.getIsbn()),
            dto.getAuthors().stream().map(Author::of).collect(Collectors.toList()),
            dto.getNumberOfPages(), null);
  }
}
