package com.curvelo.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.curvelo.adapter.input.restcontroller.dto.BookDto;
import com.curvelo.database.model.BookModel;
import java.util.List;
import org.junit.jupiter.api.Test;

class BookModelMapperTest {

  @Test
  void shouldMapperBookEntityToBookDto() {
    var book = BookModel.builder()
        .id(12)
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

    var result = BookMapper.toDto(book);

    assertThat(result.id()).isEqualTo(12);
    assertThat(result.isbn()).isEqualTo("123456789");
    assertThat(result.numberOfPages()).isEqualTo(250);
    assertThat(result.authors()).hasSize(1);
    assertThat(result.authors().get(0)).isEqualTo("J.R.R. Tolkien");
    assertThat(result.title()).isEqualTo("Hobbit");
  }

  @Test
  void shouldMapperBookDtoToBookEntity() {
    var dto = BookDto.builder()
        .id(12)
        .isbn("123456789")
        .numberOfPages(250)
        .authors(List.of("J.R.R. Tolkien"))
        .title("Hobbit")
        .build();

    var result = BookMapper.toEntity(dto);

    assertThat(result.getId()).isEqualTo(12);
    assertThat(result.getIsbn()).isEqualTo("123456789");
    assertThat(result.getNumberOfPages()).isEqualTo(250);
    assertThat(result.getAuthor()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getTitle()).isEqualTo("Hobbit");
  }

}