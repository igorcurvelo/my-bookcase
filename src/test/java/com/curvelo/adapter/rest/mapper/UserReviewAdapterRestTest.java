package com.curvelo.adapter.rest.mapper;

import static com.curvelo.ComposeDomain.createUser;
import static org.assertj.core.api.Assertions.assertThat;

import com.curvelo.core.domain.UserReview;
import org.junit.jupiter.api.Test;

class UserReviewAdapterRestTest {

  @Test
  void shouldMapperUserDomainToUserDto() {
    var user = createUser(11);

    var userReviewDomain = UserReview.of("Ótimo livro", user);

    var result = UserReviewAdapterRest.toDTO(userReviewDomain);

    assertThat(result.getUser().getId()).isEqualTo(11);
    assertThat(result.getUser().getName()).isEqualTo("Igor");
    assertThat(result.getComment()).isEqualTo("Ótimo livro");
  }

}