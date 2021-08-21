package com.curvelo.mapper;

import com.curvelo.api.dto.BookDTO;
import com.curvelo.database.model.BookModel;
import java.util.Optional;

public class BookMapper {

  private BookMapper() {}

  public static BookModel toEntity(final BookDTO bookDTO) {
    return Optional.ofNullable(bookDTO)
        .map(dto ->
            BookModel.builder()
                .id(dto.getId())
                .author(dto.getAuthor())
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .numberOfPages(bookDTO.getNumberOfPages()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

  public static BookDTO toDTO(final BookModel bookModel) {
    return Optional.ofNullable(bookModel)
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
