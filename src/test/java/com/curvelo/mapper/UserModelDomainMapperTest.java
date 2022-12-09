package com.curvelo.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.curvelo.adapter.input.restcontroller.dto.UserDto;
import com.curvelo.database.model.UserModel;
import org.junit.jupiter.api.Test;

class UserModelDomainMapperTest {

  @Test
  void shouldMapperUserEntityToUserDto() {
    var user = UserModel.builder()
        .id(99)
        .name("Igor")
        .build();

    var result = UserMapper.toDto(user);

    assertThat(result.getId()).isEqualTo(99);
    assertThat(result.getName()).isEqualTo("Igor");
  }

  @Test
  void shouldMapperBookDtoToBookEntity() {
    var user = UserDto.builder()
        .id(99)
        .name("Igor")
        .build();

    var result = UserMapper.toEntity(user);

    assertThat(result.getId()).isEqualTo(99);
    assertThat(result.getName()).isEqualTo("Igor");
  }

}