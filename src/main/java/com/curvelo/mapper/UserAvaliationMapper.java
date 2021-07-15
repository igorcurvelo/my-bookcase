package com.curvelo.mapper;

import com.curvelo.api.dto.UserAvaliationDTO;
import com.curvelo.api.dto.UserDTO;

public class UserAvaliationMapper {

  private UserAvaliationMapper() {}

  public static UserAvaliationDTO toDTO(final String comment, final UserDTO user) {
    return UserAvaliationDTO.builder()
        .comment(comment)
        .user(user)
        .build();
  }
}
