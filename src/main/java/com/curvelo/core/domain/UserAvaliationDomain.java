package com.curvelo.core.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserAvaliationDomain {

  private final String comment;
  private final UserDomain user;

}
