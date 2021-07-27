package com.curvelo.adapter.mysql.mapper;

import com.curvelo.core.domain.ReviewDomain;
import com.curvelo.database.model.ReviewModel;
import java.util.Optional;

public class ReviewAdapterMysql {

  private ReviewAdapterMysql() {}

  public static ReviewModel toEntity(final ReviewDomain reviewDomain) {
    return Optional.ofNullable(reviewDomain)
        .map(dto ->
            ReviewModel.builder()
                .id(dto.getId())
                .userModel(UserAdapterMysql.toEntity(dto.getUser()))
                .score(dto.getScore())
                .comment(dto.getComment()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

  public static ReviewDomain toDomain(final ReviewModel reviewModel) {
    return Optional.ofNullable(reviewModel)
        .map(entity ->
            ReviewDomain.builder()
                .id(entity.getId())
                .book(BookAdapterMysql.toDomain(reviewModel.getBookModel()))
                .user(UserAdapterMysql.toDomain(reviewModel.getUserModel()))
                .score(entity.getScore())
                .comment(entity.getComment()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

}
