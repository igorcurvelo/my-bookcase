package com.curvelo.adapter.rest.mapper;

import static com.curvelo.ComposeDomain.createReview;
import static com.curvelo.ComposeDomain.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.curvelo.adapter.input.restcontroller.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewAdapterRestTest {

  @Mock
  private UserAdapterRest userAdapterRest;

  @InjectMocks
  private ReviewAdapterRest reviewAdapterRest;

  @Test
  void shouldMapperReviewDomainToReviewDto() {
    var user1 = createUser(99);
    var review1 = createReview(1, user1);

    when(userAdapterRest.toDto(user1))
        .thenReturn(UserDto.builder()
            .id(user1.getId())
            .name(user1.getName())
            .build());

    final var result = reviewAdapterRest.toDto(review1);

    assertThat(result.id()).isEqualTo(1);
    assertThat(result.score()).isEqualTo(4);
    assertThat(result.comment()).isEqualTo("excelente leitura");
    assertThat(result.user().id()).isEqualTo(99);
    assertThat(result.user().name()).isEqualTo("Igor");
  }

}