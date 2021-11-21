package com.curvelo.adapter.rest.mapper;

import static com.curvelo.ComposeDomain.createUser;
import static org.assertj.core.api.Assertions.assertThat;

import com.curvelo.api.dto.UserDTO;
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

    Mockito.when(userAdapterRest.toDTO(user)).thenReturn(new UserDTO(11, "Igor"));

    final var result = userReviewAdapterRest.toDTO(userReviewDomain);

    assertThat(result.getUser().getId()).isEqualTo(11);
    assertThat(result.getUser().getName()).isEqualTo("Igor");
    assertThat(result.getComment()).isEqualTo("Ótimo livro");
  }

}