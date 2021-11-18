package com.curvelo.adapter.mysql.gateway;

import com.curvelo.adapter.mysql.mapper.ReviewAdapterMysql;
import com.curvelo.core.domain.Review;
import com.curvelo.core.repository.ReviewDomainRepository;
import com.curvelo.database.repository.ReviewRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ReviewMysqlRepository implements ReviewDomainRepository {

  private final ReviewRepository reviewRepository;

  public ReviewMysqlRepository(
      ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  @Override
  public List<Review> findByBookId(int bookId) {
    return reviewRepository.findByBookId(bookId)
        .stream().map(ReviewAdapterMysql::toDomain)
        .collect(Collectors.toList());
  }
}
