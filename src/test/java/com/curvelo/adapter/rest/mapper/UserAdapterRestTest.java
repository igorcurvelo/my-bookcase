package com.curvelo.adapter.rest.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.curvelo.core.domain.User;
import org.junit.jupiter.api.Test;

class UserAdapterRestTest {

  @Test
  void shouldMapperUserDomainToUserDto() {
    final var book = User.of(12, "Igor");

    final var result = new UserAdapterRest().toDto(book);

    assertThat(result.id()).isEqualTo(12);
    assertThat(result.name()).isEqualTo("Igor");
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenDomainIsNull() {
    assertThatThrownBy(() -> new UserAdapterRest().toDto(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

}