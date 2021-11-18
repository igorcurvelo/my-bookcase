package com.curvelo.adapter.rest.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.curvelo.core.domain.User;
import org.junit.jupiter.api.Test;

class UserAdapterRestTest {

  @Test
  void shouldMapperUserDomainToUserDto() {
    var book = User.of(12, "Igor");

    var result = UserAdapterRest.toDTO(book);

    assertThat(result.getId()).isEqualTo(12);
    assertThat(result.getName()).isEqualTo("Igor");
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenDomainIsNull() {
    assertThatThrownBy(() -> UserAdapterRest.toDTO(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

}