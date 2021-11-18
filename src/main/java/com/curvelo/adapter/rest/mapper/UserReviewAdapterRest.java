package com.curvelo.adapter.rest.mapper;

import com.curvelo.api.dto.UserReviewDTO;
import com.curvelo.core.domain.UserReview;
import java.util.Optional;

public class UserReviewAdapterRest {

  private UserReviewAdapterRest() {}

  public static UserReviewDTO toDTO(final UserReview userReview) {
    return Optional.ofNullable(userReview)
        .map(entity ->
            UserReviewDTO.builder()
                .comment(userReview.getComment())
                .user(UserAdapterRest.toDTO(userReview.getUser())).build()
        ).orElseThrow(IllegalArgumentException::new);
  }
}
