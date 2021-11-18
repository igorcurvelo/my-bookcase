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

  public TotalReviews calculateReviewsByBook(final int bookId){
    var book = bookDomainRepository.findById(bookId);

    var average = book.getAverageScore();
    var commentsByUser = book.getComments();

    return TotalReviews.of(average, commentsByUser, book);
  }
}
