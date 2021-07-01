package com.curvelo.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.curvelo.api.dto.AvaliationDTO;
import com.curvelo.domain.model.Avaliation;
import com.curvelo.domain.model.Book;
import org.junit.jupiter.api.Test;

class AvaliationMapperTest {

  @Test
  void shouldMapperAvaliationEntityToAvaliationDto() {
    var book = Book.builder()
        .id(12)
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

    var avaliation = Avaliation.builder()
        .id(21)
        .book(book)
        .comment("excelente livro")
        .score(9)
        .build();

    var result = AvaliationMapper.toDTO(avaliation);

    assertThat(result.getId()).isEqualTo(21);
    assertThat(result.getBook().getId()).isEqualTo(12);
    assertThat(result.getComment()).isEqualTo("excelente livro");
    assertThat(result.getScore()).isEqualTo(9);
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenEntityIsNull() {
    assertThatThrownBy(() -> AvaliationMapper.toDTO(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void shouldMapperAvaliationDtoToAvaliationEntity() {
    var avaliation = AvaliationDTO.builder()
        .id(21)
        .comment("excelente livro")
        .score(9)
        .build();

    var result = AvaliationMapper.toEntity(avaliation);

    assertThat(result.getId()).isEqualTo(21);
    assertThat(result.getComment()).isEqualTo("excelente livro");
    assertThat(result.getScore()).isEqualTo(9);
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenDtoIsNull() {
    assertThatThrownBy(() -> AvaliationMapper.toEntity(null))
        .isInstanceOf(IllegalArgumentException.class);
  }
}