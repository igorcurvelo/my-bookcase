package com.curvelo.api.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDTO {

  private final Integer id;
  private final String name;

}
