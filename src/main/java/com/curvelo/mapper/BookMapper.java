package com.curvelo.mapper;

import com.curvelo.api.dto.BookDTO;
import com.curvelo.model.Book;
import java.util.Optional;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookMapper {

  public static Book toEntity(final BookDTO bookDTO) {
    return Optional.ofNullable(bookDTO)
        .map(dto ->
            Book.builder()
                .id(dto.getId())
                .author(dto.getAuthor())
                .title(dto.getTitle())
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
                .numberOfPages(entity.getNumberOfPages()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }
}
