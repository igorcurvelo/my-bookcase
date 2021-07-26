package com.curvelo.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.curvelo.api.dto.AvaliationDTO;
import com.curvelo.api.dto.UserDTO;
import com.curvelo.database.model.AvaliationModel;
import com.curvelo.database.model.BookModel;
import com.curvelo.database.model.UserModel;
import org.junit.jupiter.api.Test;

class AvaliationModelMapperTest {

  @Test
  void shouldMapperAvaliationEntityToAvaliationDto() {
    var book = BookModel.builder()
        .id(12)
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

    var user = UserModel.builder()
        .id(99)
        .name("Igor")
        .build();

    var avaliation = AvaliationModel.builder()
        .id(21)
        .bookModel(book)
        .userModel(user)
        .comment("excelente livro")
        .score(9)
        .build();

    var result = AvaliationMapper.toDTO(avaliation);

    assertThat(result.getId()).isEqualTo(21);
    assertThat(result.getBook().getId()).isEqualTo(12);
    assertThat(result.getUser().getId()).isEqualTo(99);
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
    var user = UserDTO.builder()
        .id(99)
        .name("Igor")
        .build();

    var avaliation = AvaliationDTO.builder()
        .id(21)
        .user(user)
        .comment("excelente livro")
        .score(9)
        .build();

    var result = AvaliationMapper.toEntity(avaliation);

    assertThat(result.getId()).isEqualTo(21);
    assertThat(result.getUserModel().getId()).isEqualTo(99);
    assertThat(result.getComment()).isEqualTo("excelente livro");
    assertThat(result.getScore()).isEqualTo(9);
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenDtoIsNull() {
    assertThatThrownBy(() -> AvaliationMapper.toEntity(null))
        .isInstanceOf(IllegalArgumentException.class);
  }
}