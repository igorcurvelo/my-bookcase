package com.curvelo.adapter.input.restcontroller.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewDto {

  private final Integer id;
  private final Integer score;
  private final String comment;
  private final BookDto book;
  private final UserDto user;

}
