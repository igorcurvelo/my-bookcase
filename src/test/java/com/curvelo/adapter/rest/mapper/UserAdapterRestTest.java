package com.curvelo.adapter.rest.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.curvelo.core.domain.User;
import org.junit.jupiter.api.Test;

class UserAdapterRestTest {

  @Test
  void shouldMapperUserDomainToUserDto() {
    final var book = User.of(12, "Igor");

    final var result = new UserAdapterRest().toDTO(book);

    assertThat(result.getId()).isEqualTo(12);
    assertThat(result.getName()).isEqualTo("Igor");
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenDomainIsNull() {
    assertThatThrownBy(() -> new UserAdapterRest().toDTO(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

}