package com.curvelo.adapter.mysql.gateway;

import static com.curvelo.ComposeModel.createBook;
import static com.curvelo.ComposeModel.createReview;
import static com.curvelo.ComposeModel.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.curvelo.adapter.mysql.mapper.ReviewAdapterMysql;
import com.curvelo.core.domain.Review;
import com.curvelo.core.domain.User;
import com.curvelo.database.repository.ReviewRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewMysqlRepositoryTest {

  @Mock
  private ReviewRepository reviewRepository;

  @Mock
  private ReviewAdapterMysql reviewAdapterMysql;

  @InjectMocks
  private ReviewMysqlRepository reviewMysqlRepository;

  @Test
  void shouldReturnABookById() {

    final var bookModel1 = createBook(11).build();

    final var user1 = createUser(21).build();
    final var user2 = createUser(22).build();

    final var reviewModel1 = createReview(121, user1, bookModel1).build();
    final var reviewModel2 = createReview(122, user2, bookModel1).build();

    final var review1 = Review.of(
        121,
        4,
        "excelente leitura",
        User.of(21, "name")
    );

    final var review2 = Review.of(
        122,
        4,
        "excelente leitura",
        User.of(22, "name")
    );

    when(reviewRepository.findByBookId(12))
        .thenReturn(List.of(reviewModel1, reviewModel2));

    when(reviewAdapterMysql.toDomain(reviewModel1))
        .thenReturn(review1);

    when(reviewAdapterMysql.toDomain(reviewModel2))
        .thenReturn(review2);

    var result = reviewMysqlRepository.findByBookId(12);

    assertThat(result).hasSize(2);
    assertThat(result.get(0).getId()).isEqualTo(121);
    assertThat(result.get(0).getComment()).isEqualTo("excelente leitura");
    assertThat(result.get(0).getScore()).isEqualTo(4);
    assertThat(result.get(0).getUser().getId()).isEqualTo(21);

    assertThat(result.get(1).getId()).isEqualTo(122);
    assertThat(result.get(1).getComment()).isEqualTo("excelente leitura");
    assertThat(result.get(1).getScore()).isEqualTo(4);
    assertThat(result.get(1).getUser().getId()).isEqualTo(22);

    verify(reviewRepository, times(1)).findByBookId(12);
  }

  @Test
  void shouldReturnExceptionWhenBookDoesNotExist() {
    when(reviewRepository.findByBookId(123))
        .thenThrow(new EntityNotFoundException("Book not found"));

    assertThatThrownBy(() -> reviewMysqlRepository.findByBookId(123))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("Book not found");
  }

}