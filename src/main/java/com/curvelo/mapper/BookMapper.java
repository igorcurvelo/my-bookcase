package com.curvelo.mapper;

import com.curvelo.api.dto.BookDTO;
import com.curvelo.database.model.BookModel;

public class BookMapper {

  private BookMapper() {}

  public static BookModel toEntity(final BookDTO bookDTO) {
    return BookModel.builder()
                .id(bookDTO.getId())
                .author(bookDTO.getAuthor())
                .title(bookDTO.getTitle())
                .isbn(bookDTO.getIsbn())
                .numberOfPages(bookDTO.getNumberOfPages()).build();
  }

  public static BookDTO toDTO(final BookModel bookModel) {
    return BookDTO.builder()
                .id(bookModel.getId())
                .author(bookModel.getAuthor())
                .title(bookModel.getTitle())
                .isbn(bookModel.getIsbn())
                .numberOfPages(bookModel.getNumberOfPages()).build();
  }
}
