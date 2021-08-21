package com.curvelo.core.usecase;

import com.curvelo.core.domain.ReviewDomain;
import com.curvelo.core.domain.BookDomain;
import com.curvelo.core.domain.TotalReviewsDomain;
import com.curvelo.core.domain.UserReviewDomain;
import com.curvelo.core.repository.ReviewDomainRepository;
import com.curvelo.core.repository.BookDomainRepository;
import java.util.Collections;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CalculateReviewsUseCase {

  private final ReviewDomainRepository reviewDomainRepository;
  private final BookDomainRepository bookDomainRepository;

  public CalculateReviewsUseCase(
      ReviewDomainRepository reviewDomainRepository,
      BookDomainRepository bookDomainRepository) {
    this.reviewDomainRepository = reviewDomainRepository;
    this.bookDomainRepository = bookDomainRepository;
  }

  public TotalReviewsDomain calculateReviewsByBook(final int bookId) throws EntityNotFoundException {
    var book = bookDomainRepository.findById(bookId);

    var reviews = reviewDomainRepository.findByBookId(
        bookId);

    if (reviews.isEmpty()) {
      return defaultReview(book);
    }

    var average = reviews.stream()
        .map(ReviewDomain::getScore)
        .mapToDouble(Integer::doubleValue)
        .average()
        .orElse(0.0);

    var commentsByUser = reviews.stream()
        .map(review ->
            UserReviewDomain.builder()
                .comment(review.getComment())
                .user(review.getUser())
                .build())
        .collect(Collectors.toList());

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
