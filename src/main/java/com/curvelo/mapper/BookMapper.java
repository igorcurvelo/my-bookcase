package com.curvelo.mapper;

import com.curvelo.adapter.input.restcontroller.dto.BookDTO;
import com.curvelo.constant.Constants;
import com.curvelo.database.model.BookModel;
import java.util.List;

/**
 * quando todos os endpoints forem migrados para o use-case, esse mapper nao sera mais necessario.
 */
public class BookMapper {

  private BookMapper() {}

  public static BookModel toEntity(final BookDTO bookDTO) {
    return BookModel.builder()
                .id(bookDTO.getId())
                .author(String.join(Constants.SEPARATOR_AUTHOR_MODEL, bookDTO.getAuthors()))
                .title(bookDTO.getTitle())
                .isbn(bookDTO.getIsbn())
                .numberOfPages(bookDTO.getNumberOfPages()).build();
  }

  public static BookDTO toDTO(final BookModel bookModel) {
    return BookDTO.builder()
                .id(bookModel.getId())
                .authors(List.of(bookModel.getAuthor()))
                .title(bookModel.getTitle())
                .isbn(bookModel.getIsbn())
                .numberOfPages(bookModel.getNumberOfPages()).build();
  }
}
