package com.curvelo.core.usecase;

import com.curvelo.core.domain.Review;
import com.curvelo.core.repository.ReviewDomainRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetterReviewUseCase {

  private final ReviewDomainRepository reviewDomainRepository;

  public GetterReviewUseCase(final ReviewDomainRepository reviewDomainRepository) {
    this.reviewDomainRepository = reviewDomainRepository;
  }

  public List<Review> findByBook(int bookId) {
    return reviewDomainRepository.findByBookId(bookId);
  }
}
