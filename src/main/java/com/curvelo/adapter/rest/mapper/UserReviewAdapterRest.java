package com.curvelo.adapter.rest.mapper;

import com.curvelo.api.dto.UserReviewDTO;
import com.curvelo.core.domain.UserReviewDomain;
import java.util.Optional;

public class UserReviewAdapterRest {

  private UserReviewAdapterRest() {}

  public static UserReviewDTO toDTO(final UserReviewDomain userReviewDomain) {
    return Optional.ofNullable(userReviewDomain)
        .map(entity ->
            UserReviewDTO.builder()
                .comment(userReviewDomain.getComment())
                .user(UserAdapterRest.toDTO(userReviewDomain.getUser())).build()
        ).orElseThrow(IllegalArgumentException::new);
  }
}
