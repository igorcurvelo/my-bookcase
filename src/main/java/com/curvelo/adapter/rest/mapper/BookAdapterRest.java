package com.curvelo.adapter.rest.mapper;

import com.curvelo.adapter.input.restcontroller.dto.BookDTO;
import com.curvelo.core.domain.Author;
import com.curvelo.core.domain.Book;
import com.curvelo.core.domain.Isbn;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookAdapterRest {

  public BookDTO toDTO(final Book book) {
    return BookDTO.builder()
        .id(book.getId())
        .authors(book.getAuthors().stream()
            .map(Author::getName).collect(Collectors.toList()))
        .title(book.getTitle())
        .isbn(book.getIsbn().getValue())
        .numberOfPages(book.getNumberOfPages()).build();
  }

  public Book toDomain(final BookDTO dto) {
    return Book.of(
            dto.getId(),
            dto.getTitle(),
            Isbn.of(dto.getIsbn()),
            dto.getAuthors().stream().map(Author::of).collect(Collectors.toList()),
            dto.getNumberOfPages(), null);
  }
}
