package com.curvelo.mapper;

import com.curvelo.adapter.input.restcontroller.dto.UserReviewDTO;
import com.curvelo.adapter.input.restcontroller.dto.UserDTO;

public class UserReviewMapper {

  private UserReviewMapper() {}

  public static UserReviewDTO toDTO(final String comment, final UserDTO user) {
    return UserReviewDTO.builder()
        .comment(comment)
        .user(user)
        .build();
  }
}
