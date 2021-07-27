package com.curvelo.core.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserReviewDomain {

  private final String comment;
  private final UserDomain user;

}
