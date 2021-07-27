package com.curvelo.api.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewDTO {

  private final Integer id;
  private final Integer score;
  private final String comment;
  private final BookDTO book;
  private final UserDTO user;

}
