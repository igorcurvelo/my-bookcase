package com.curvelo.core.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.curvelo.core.domain.Review;
import com.curvelo.core.domain.User;
import com.curvelo.core.repository.ReviewDomainRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreatorReviewUseCaseTest {

  @Mock
  private ReviewDomainRepository reviewDomainRepository;

  @InjectMocks
  private CreatorReviewUseCase creatorReviewUseCase;

  @Test
  void shouldCreateANewReview() {
    final var bookId = 123;
    final var user = User.of(2, "name");
    final var review = Review.of(
        null, 4, "comment", user
    );

    final var reviewSaved = Review.of(
        1, 4, "comment", user
    );

    when(reviewDomainRepository.save(bookId, review))
        .thenReturn(reviewSaved);

    final var result = creatorReviewUseCase.create(bookId, review);

    assertThat(result.getId()).isEqualTo(1);
    assertThat(result.getScore()).isEqualTo(4);
    assertThat(result.getComment()).isEqualTo("comment");
    assertThat(result.getUser().getId()).isEqualTo(2);
    assertThat(result.getUser().getName()).isEqualTo("name");
  }

}