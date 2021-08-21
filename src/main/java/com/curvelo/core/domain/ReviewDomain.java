package com.curvelo.core.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewDomain {

  private final Integer id;
  private final Integer score;
  private final String comment;
  private final BookDomain book;
  private final UserDomain user;

}
