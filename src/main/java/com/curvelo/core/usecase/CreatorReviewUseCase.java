package com.curvelo.core.usecase;

import com.curvelo.core.domain.Review;
import com.curvelo.core.repository.ReviewDomainRepository;
import org.springframework.stereotype.Component;

@Component
public class CreatorReviewUseCase {

  private final ReviewDomainRepository reviewDomainRepository;

  public CreatorReviewUseCase(final ReviewDomainRepository reviewDomainRepository) {
    this.reviewDomainRepository = reviewDomainRepository;
  }

  public Review create(int bookId, Review review) {
    return reviewDomainRepository.save(bookId, review);
  }
}
