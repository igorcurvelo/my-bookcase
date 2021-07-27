package com.curvelo.mapper;

import com.curvelo.api.dto.UserReviewDTO;
import com.curvelo.api.dto.UserDTO;

public class UserReviewMapper {

  private UserReviewMapper() {}

  public static UserReviewDTO toDTO(final String comment, final UserDTO user) {
    return UserReviewDTO.builder()
        .comment(comment)
        .user(user)
        .build();
  }
}
