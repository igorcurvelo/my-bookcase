package com.curvelo.mapper;

import com.curvelo.adapter.input.restcontroller.dto.UserDto;
import com.curvelo.adapter.input.restcontroller.dto.UserReviewDto;

public class UserReviewMapper {

  private UserReviewMapper() {}

  public static UserReviewDto toDto(final String comment, final UserDto user) {
    return UserReviewDto.builder()
        .comment(comment)
        .user(user)
        .build();
  }
}
