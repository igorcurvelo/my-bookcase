package com.curvelo.adapter.mysql.mapper;

import com.curvelo.core.domain.Review;
import com.curvelo.database.model.ReviewModel;

public class ReviewAdapterMysql {

  private ReviewAdapterMysql() {}

  public static Review toDomain(final ReviewModel reviewModel) {
    return Review.of(
        reviewModel.getId(),
        reviewModel.getScore(),
        reviewModel.getComment(),
        UserAdapterMysql.toDomain(reviewModel.getUser()));
  }

}
