package com.curvelo.mapper;

import com.curvelo.adapter.input.restcontroller.dto.BookDto;
import com.curvelo.constant.Constants;
import com.curvelo.database.model.BookModel;
import java.util.List;

/**
 * quando todos os endpoints forem migrados para o use-case, esse mapper nao sera mais necessario.
 */
public class BookMapper {

  private BookMapper() {}

  public static BookModel toEntity(final BookDto bookDto) {
    return BookModel.builder()
                .id(bookDto.getId())
                .author(String.join(Constants.SEPARATOR_AUTHOR_MODEL, bookDto.getAuthors()))
                .title(bookDto.getTitle())
                .isbn(bookDto.getIsbn())
                .numberOfPages(bookDto.getNumberOfPages()).build();
  }

  public static BookDto toDto(final BookModel bookModel) {
    return BookDto.builder()
                .id(bookModel.getId())
                .authors(List.of(bookModel.getAuthor()))
                .title(bookModel.getTitle())
                .isbn(bookModel.getIsbn())
                .numberOfPages(bookModel.getNumberOfPages()).build();
  }
}
