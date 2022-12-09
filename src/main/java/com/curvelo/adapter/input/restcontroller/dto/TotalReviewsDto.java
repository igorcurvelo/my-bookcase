package com.curvelo.adapter.input.restcontroller.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TotalReviewsDto {

  private final Double score;
  private final List<UserReviewDto> comments;
  private final BookDto book;

}
