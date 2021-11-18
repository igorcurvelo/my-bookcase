package com.curvelo.core.usecase;

import com.curvelo.core.repository.BookDomainRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collections;

import static com.curvelo.ComposeDomain.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculateReviewModelUseCaseTest {

  @Mock
  private BookDomainRepository bookDomainRepository;

  @InjectMocks
  private CalculateReviewsUseCase calculateReviewsUseCase;

  @Test
  void shouldCalculateTwoReviewsByBook() {
    var user1 = createUser(99).build();
    var review1 = createReview(1, user1).build();

    var user2 = createUser(98).name("Curvelo").build();
    var review2 = createReview(2, user2)
        .comment("boa leitura")
        .score(3)
        .build();

    var book = createBook(11, Arrays.asList(review1, review2));

    when(bookDomainRepository.findById(book.getId()))
        .thenReturn(book);

    var result = calculateReviewsUseCase.calculateReviewsByBook(book.getId());

    assertThat(result.getScore()).isEqualTo(3.5);
    assertThat(result.getBook().getId()).isEqualTo(book.getId());
    assertThat(result.getBook().getTitle()).isEqualTo(book.getTitle());
    assertThat(result.getComments()).hasSize(2);
    assertThat(result.getComments().get(0).getUser().getId()).isEqualTo(user1.getId());
    assertThat(result.getComments().get(0).getUser().getName()).isEqualTo(user1.getName());
    assertThat(result.getComments().get(0).getComment()).isEqualTo(review1.getComment());

    assertThat(result.getComments().get(1).getUser().getId()).isEqualTo(user2.getId());
    assertThat(result.getComments().get(1).getUser().getName()).isEqualTo(user2.getName());
    assertThat(result.getComments().get(1).getComment()).isEqualTo(review2.getComment());
  }

  @Test
  void shouldReturnDefaultTotalReviewsByBookWhenBookDoesNotExist() {
    when(bookDomainRepository.findById(123))
        .thenThrow(new EntityNotFoundException("Book not found"));

    assertThatThrownBy(() -> calculateReviewsUseCase.calculateReviewsByBook(123))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("Book not found");
  }

  @Test
  void shouldReturnDefaultTotalReviewsByBookWhenReviewsDoesNotExist() {
    var book = createBook(11, Collections.emptyList());

    when(bookDomainRepository.findById(book.getId()))
        .thenReturn(book);

    var result = calculateReviewsUseCase.calculateReviewsByBook(book.getId());

    assertThat(result.getScore()).isZero();
    assertThat(result.getBook().getId()).isEqualTo(book.getId());
    assertThat(result.getBook().getTitle()).isEqualTo(book.getTitle());
    assertThat(result.getComments()).isEmpty();
  }

}