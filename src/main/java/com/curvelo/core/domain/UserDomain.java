package com.curvelo.core.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDomain {

  private final Integer id;
  private final String name;

}
