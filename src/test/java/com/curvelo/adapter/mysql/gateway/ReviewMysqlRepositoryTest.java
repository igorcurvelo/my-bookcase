package com.curvelo.adapter.mysql.gateway;

import static com.curvelo.ComposeModel.createBook;
import static com.curvelo.ComposeModel.createReview;
import static com.curvelo.ComposeModel.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

  @InjectMocks
  private ReviewMysqlRepository reviewMysqlRepository;

  @Test
  void shouldReturnABookById() {

    var bookModel1 = createBook(11).build();
    var bookModel2 = createBook(11).build();

    var user1 = createUser(21).build();
    var user2 = createUser(22).build();

    var review1 = createReview(121, user1, bookModel1).build();
    var review2 = createReview(122, user2, bookModel1).build();
    var review3 = createReview(123, user2, bookModel2).build();

    when(reviewRepository.findByBookId(12))
        .thenReturn(List.of(review1, review2));

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