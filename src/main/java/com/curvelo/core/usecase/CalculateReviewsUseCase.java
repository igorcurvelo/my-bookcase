package com.curvelo.core.usecase;

import com.curvelo.core.domain.BookDomain;
import com.curvelo.core.domain.TotalReviewsDomain;
import com.curvelo.core.repository.BookDomainRepository;
import java.util.Collections;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CalculateReviewsUseCase {

  private final BookDomainRepository bookDomainRepository;

  public CalculateReviewsUseCase(
      BookDomainRepository bookDomainRepository) {
    this.bookDomainRepository = bookDomainRepository;
  }

  public TotalReviewsDomain calculateReviewsByBook(final int bookId) throws EntityNotFoundException {
    var book = bookDomainRepository.findById(bookId);

    if (book.getReviews().isEmpty()) {
      // todo verificar se eh necessario
      return defaultReview(book);
    }

    var average = book.getAverageScore();
    var commentsByUser = book.getComments();

    return TotalReviewsDomain.builder()
        .book(book)
        .comments(commentsByUser)
        .score(average)
        .build();
  }

  private TotalReviewsDomain defaultReview(final BookDomain book) {
    return TotalReviewsDomain.builder()
        .book(book)
        .comments(Collections.emptyList())
        .score(0.0)
        .build();
  }
}
