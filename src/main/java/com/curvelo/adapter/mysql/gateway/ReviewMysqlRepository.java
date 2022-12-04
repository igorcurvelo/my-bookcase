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
  private final ReviewAdapterMysql reviewAdapterMysql;

  public ReviewMysqlRepository(
      final ReviewRepository reviewRepository,
      final ReviewAdapterMysql reviewAdapterMysql) {
    this.reviewRepository = reviewRepository;
    this.reviewAdapterMysql = reviewAdapterMysql;
  }

  @Override
  public List<Review> findByBookId(int bookId) {
    return reviewRepository.findByBookId(bookId)
        .stream().map(reviewAdapterMysql::toDomain)
        .collect(Collectors.toList());
  }
}
