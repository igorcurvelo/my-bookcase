package com.curvelo.adapter.rest.mapper;

import static com.curvelo.ComposeDomain.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.curvelo.core.domain.UserReviewDomain;
import org.junit.jupiter.api.Test;

class UserReviewAdapterRestTest {

  @Test
  void shouldMapperUserDomainToUserDto() {
    var user = createUser(11).build();

    var userReviewDomain = UserReviewDomain.builder()
        .user(user).comment("Ótimo livro").build();

    var result = UserReviewAdapterRest.toDTO(userReviewDomain);

    assertThat(result.getUser().getId()).isEqualTo(11);
    assertThat(result.getUser().getName()).isEqualTo("Igor");
    assertThat(result.getComment()).isEqualTo("Ótimo livro");
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenDomainIsNull() {
    assertThatThrownBy(() -> BookAdapterRest.toDTO(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

}