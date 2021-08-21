package com.curvelo.api.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TotalReviewsDTO {

  private final Double score;
  private final List<UserReviewDTO> comments;
  private final BookDTO book;

}
