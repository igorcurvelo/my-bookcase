package com.curvelo.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.curvelo.api.dto.BookDTO;
import com.curvelo.domain.model.Book;
import org.junit.jupiter.api.Test;

class BookMapperTest {

  @Test
  void shouldMapperBookEntityToBookDto() {
    var book = Book.builder()
        .id(12)
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

    var result = BookMapper.toDTO(book);

    assertThat(result.getId()).isEqualTo(12);
    assertThat(result.getIsbn()).isEqualTo("123456789");
    assertThat(result.getNumberOfPages()).isEqualTo(250);
    assertThat(result.getAuthor()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getTitle()).isEqualTo("Hobbit");
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenEntityIsNull() {
    assertThatThrownBy(() -> BookMapper.toDTO(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void shouldMapperBookDtoToBookEntity() {
    var dto = BookDTO.builder()
        .id(12)
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

    var result = BookMapper.toEntity(dto);

    assertThat(result.getId()).isEqualTo(12);
    assertThat(result.getIsbn()).isEqualTo("123456789");
    assertThat(result.getNumberOfPages()).isEqualTo(250);
    assertThat(result.getAuthor()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getTitle()).isEqualTo("Hobbit");
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenDtoIsNull() {
    assertThatThrownBy(() -> BookMapper.toEntity(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

}