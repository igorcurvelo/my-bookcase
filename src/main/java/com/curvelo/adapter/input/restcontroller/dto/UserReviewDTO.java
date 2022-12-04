package com.curvelo.adapter.input.restcontroller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserReviewDTO {

  private final String comment;
  private final UserDTO user;

}