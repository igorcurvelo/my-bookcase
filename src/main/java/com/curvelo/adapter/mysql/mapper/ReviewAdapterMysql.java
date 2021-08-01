package com.curvelo.adapter.mysql.mapper;

import com.curvelo.core.domain.ReviewDomain;
import com.curvelo.database.model.ReviewModel;
import java.util.Optional;

public class ReviewAdapterMysql {

  private ReviewAdapterMysql() {}

  public static ReviewDomain toDomain(final ReviewModel reviewModel) {
    return Optional.ofNullable(reviewModel)
        .map(entity ->
            ReviewDomain.builder()
                .id(entity.getId())
                .book(BookAdapterMysql.toDomain(reviewModel.getBook()))
                .user(UserAdapterMysql.toDomain(reviewModel.getUser()))
                .score(entity.getScore())
                .comment(entity.getComment()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

}
