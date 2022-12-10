package com.curvelo.adapter.mysql.mapper;

import com.curvelo.core.domain.Review;
import com.curvelo.database.model.ReviewModel;
import org.springframework.stereotype.Component;

@Component
public class ReviewAdapterMysql {

  private final UserAdapterMysql userAdapterMysql;

  public ReviewAdapterMysql(UserAdapterMysql userAdapterMysql) {
    this.userAdapterMysql = userAdapterMysql;
  }

  public Review toDomain(final ReviewModel reviewModel) {
    return Review.of(
        reviewModel.getId(),
        reviewModel.getScore(),
        reviewModel.getComment(),
        userAdapterMysql.toDomain(reviewModel.getUser()));
  }

  public ReviewModel toModel(Review review) {
    return ReviewModel.builder()
        .id(review.getId())
        .score(review.getScore())
        .comment(review.getComment())
        .user(userAdapterMysql.toModel(review.getUser()))
        .build();
  }
}
