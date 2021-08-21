package com.curvelo.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.curvelo.api.dto.UserDTO;
import com.curvelo.database.model.UserModel;
import org.junit.jupiter.api.Test;

class UserModelDomainMapperTest {

  @Test
  void shouldMapperUserEntityToUserDto() {
    var user = UserModel.builder()
        .id(99)
        .name("Igor")
        .build();

    var result = UserMapper.toDTO(user);

    assertThat(result.getId()).isEqualTo(99);
    assertThat(result.getName()).isEqualTo("Igor");
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenEntityIsNull() {
    assertThatThrownBy(() -> UserMapper.toDTO(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void shouldMapperBookDtoToBookEntity() {
    var user = UserDTO.builder()
        .id(99)
        .name("Igor")
        .build();

    var result = UserMapper.toEntity(user);

    assertThat(result.getId()).isEqualTo(99);
    assertThat(result.getName()).isEqualTo("Igor");
  }

}