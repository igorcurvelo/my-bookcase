package com.curvelo.adapter.mysql.mapper;

import com.curvelo.core.domain.ReviewDomain;
import com.curvelo.database.model.ReviewModel;

public class ReviewAdapterMysql {

  private ReviewAdapterMysql() {}

  public static ReviewDomain toDomain(final ReviewModel reviewModel) {
    return ReviewDomain.builder()
                .id(reviewModel.getId())
                .user(UserAdapterMysql.toDomain(reviewModel.getUser()))
                .score(reviewModel.getScore())
                .comment(reviewModel.getComment()).build();
  }

}
