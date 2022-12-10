package com.curvelo.core.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.curvelo.core.domain.Review;
import com.curvelo.core.domain.User;
import com.curvelo.core.repository.ReviewDomainRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetterReviewUseCaseTest {

  @InjectMocks
  private GetterReviewUseCase getterReviewUseCase;

  @Mock
  private ReviewDomainRepository reviewDomainRepository;

  @Test
  void shouldReturnAllReviewsByBookId() {
    final var review1 = Review.of(
        121,
        4,
        "excelente leitura",
        User.of(21, "name")
    );

    when(reviewDomainRepository.findByBookId(1))
        .thenReturn(List.of(review1));

    final var result = getterReviewUseCase.findByBook(1);

    assertThat(result).hasSize(1);
    assertThat(result.get(0).getId()).isEqualTo(121);
  }

}