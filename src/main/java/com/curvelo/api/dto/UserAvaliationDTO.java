package com.curvelo.api.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserAvaliationDTO {

  private final String comment;
  private final UserDTO user;

}
