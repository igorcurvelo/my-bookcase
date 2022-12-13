package com.curvelo.adapter.rest.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.curvelo.adapter.input.restcontroller.dto.UserDto;
import com.curvelo.core.domain.User;
import org.junit.jupiter.api.Test;

class UserAdapterRestTest {

  @Test
  void shouldMapperUserDomainToUserDto() {
    final var user = User.of(12, "Igor");

    final var result = new UserAdapterRest().toDto(user);

    assertThat(result.id()).isEqualTo(12);
    assertThat(result.name()).isEqualTo("Igor");
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenDomainIsNull() {
    assertThatThrownBy(() -> new UserAdapterRest().toDto(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void shouldMapperUserDtoToUserDomain() {
    final var user = UserDto.builder().id(12).name("Igor").build();

    final var result = new UserAdapterRest().toDomain(user);

    assertThat(result.getId()).isEqualTo(12);
    assertThat(result.getName()).isEqualTo("Igor");
  }

}