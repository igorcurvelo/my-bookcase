package com.curvelo.adapter.rest.mapper;

import com.curvelo.adapter.input.restcontroller.dto.BookDto;
import com.curvelo.core.domain.Author;
import com.curvelo.core.domain.Book;
import com.curvelo.core.domain.Isbn;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class BookAdapterRest {

  public BookDto toDto(final Book book) {
    return BookDto.builder()
        .id(book.getId())
        .authors(book.getAuthors().stream()
            .map(Author::getName).collect(Collectors.toList()))
        .title(book.getTitle())
        .isbn(book.getIsbn().getValue())
        .numberOfPages(book.getNumberOfPages()).build();
  }

  public Book toDomain(final BookDto dto) {
    return Book.of(
            dto.id(),
            dto.title(),
            Isbn.of(dto.isbn()),
            dto.authors().stream().map(Author::of).collect(Collectors.toList()),
            dto.numberOfPages(), null);
  }
}
