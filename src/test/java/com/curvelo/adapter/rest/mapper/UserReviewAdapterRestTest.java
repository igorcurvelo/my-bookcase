package com.curvelo.adapter.rest.mapper;

import static com.curvelo.ComposeDomain.createUser;
import static org.assertj.core.api.Assertions.assertThat;

import com.curvelo.adapter.input.restcontroller.dto.UserDto;
import com.curvelo.core.domain.UserReview;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserReviewAdapterRestTest {

  @Mock
  private UserAdapterRest userAdapterRest;

  @InjectMocks
  private UserReviewAdapterRest userReviewAdapterRest;

  @Test
  void shouldMapperUserReviewDomainToUserReviewDto() {
    final var user = createUser(11);

    final var userReviewDomain = UserReview.of("Ótimo livro", user);

    Mockito.when(userAdapterRest.toDto(user)).thenReturn(new UserDto(11, "Igor"));

    final var result = userReviewAdapterRest.toDto(userReviewDomain);

    assertThat(result.user().id()).isEqualTo(11);
    assertThat(result.user().name()).isEqualTo("Igor");
    assertThat(result.comment()).isEqualTo("Ótimo livro");
  }

}