package com.curvelo.core.usecase;

import com.curvelo.core.domain.TotalReviews;
import com.curvelo.core.repository.BookDomainRepository;
import org.springframework.stereotype.Component;

@Component
public class CalculateReviewsUseCase {

  private final BookDomainRepository bookDomainRepository;

  public CalculateReviewsUseCase(
      BookDomainRepository bookDomainRepository) {
    this.bookDomainRepository = bookDomainRepository;
  }

  public TotalReviews calculateReviewsByBook(final int bookId) {
    final var book = bookDomainRepository.findById(bookId);

    final var average = book.getAverageScore();
    final var commentsByUser = book.getComments();

    return TotalReviews.of(average, commentsByUser, book);
  }
}
