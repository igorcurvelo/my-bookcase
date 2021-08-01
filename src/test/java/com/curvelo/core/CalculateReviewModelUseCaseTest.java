package com.curvelo.core;

import static com.curvelo.ComposeDomain.createBook;
import static com.curvelo.ComposeDomain.createReview;
import static com.curvelo.ComposeDomain.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.curvelo.core.repository.BookDomainRepository;
import com.curvelo.core.repository.ReviewDomainRepository;
import com.curvelo.core.usecase.CalculateReviewsUseCase;
import java.util.Collections;
import javax.persistence.EntityNotFoundException;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CalculateReviewModelUseCaseTest {

  @Mock
  private ReviewDomainRepository reviewDomainRepository;

  @Mock
  private BookDomainRepository bookDomainRepository;

  @InjectMocks
  private CalculateReviewsUseCase calculateReviewsUseCase;

  @Test
  public void shouldCalculateTwoReviewsByBook() {
    var book = createBook(11).build();
    var user1 = createUser(99).build();
    var review1 = createReview(1, user1, book).build();

    var user2 = createUser(98).name("Curvelo").build();
    var review2 = createReview(2, user2, book)
        .comment("boa leitura")
        .score(3)
        .build();

    when(reviewDomainRepository.findByBookId(book.getId()))
        .thenReturn(Lists.list(review1, review2));

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
  public void shouldReturnDefaultTotalReviewsByBookWhenBookDoesNotExist() {
    when(bookDomainRepository.findById(123))
        .thenThrow(new EntityNotFoundException("Book not found"));

    assertThatThrownBy(() -> calculateReviewsUseCase.calculateReviewsByBook(123))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("Book not found");
  }

  @Test
  public void shouldReturnDefaultTotalReviewsByBookWhenReviewsDoesNotExist() {
    var book = createBook(11).build();

    when(bookDomainRepository.findById(book.getId()))
        .thenReturn(book);

    when(reviewDomainRepository.findByBookId(book.getId()))
        .thenReturn(Collections.emptyList());

    var result = calculateReviewsUseCase.calculateReviewsByBook(book.getId());

    assertThat(result.getScore()).isEqualTo(0.0);
    assertThat(result.getBook().getId()).isEqualTo(book.getId());
    assertThat(result.getBook().getTitle()).isEqualTo(book.getTitle());
    assertThat(result.getComments()).hasSize(0);
  }

}