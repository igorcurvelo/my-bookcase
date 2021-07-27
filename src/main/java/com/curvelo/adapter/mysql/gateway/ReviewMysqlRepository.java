package com.curvelo.adapter.mysql.gateway;

import com.curvelo.adapter.mysql.mapper.ReviewAdapterMysql;
import com.curvelo.core.domain.ReviewDomain;
import com.curvelo.core.repository.ReviewDomainRepository;
import com.curvelo.database.repository.ReviewRepository;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewMysqlRepository implements ReviewDomainRepository {

  private final ReviewRepository reviewRepository;

  public ReviewMysqlRepository(
      ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  @Override
  public List<ReviewDomain> findByBookId(int bookId) {
    return reviewRepository.findByBookId(bookId)
        .stream().map(ReviewAdapterMysql::toDomain)
        .collect(Collectors.toList());
  }
}
