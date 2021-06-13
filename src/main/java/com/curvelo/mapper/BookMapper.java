package com.curvelo.mapper;

import com.curvelo.api.dto.BookDTO;
import com.curvelo.domain.model.Book;
import java.util.Optional;

public class BookMapper {

  private BookMapper() {}

  public static Book toEntity(final BookDTO bookDTO) {
    return Optional.ofNullable(bookDTO)
        .map(dto ->
            Book.builder()
                .id(dto.getId())
                .author(dto.getAuthor())
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .numberOfPages(bookDTO.getNumberOfPages()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

  public static BookDTO toDTO(final Book book) {
    return Optional.ofNullable(book)
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
