package com.curvelo.mapper;

import com.curvelo.api.dto.UserDTO;
import com.curvelo.domain.model.User;
import java.util.Optional;

public class UserMapper {

  private UserMapper() {}

  public static User toEntity(final UserDTO userDTO) {
    return Optional.ofNullable(userDTO)
        .map(dto ->
            User.builder()
                .id(dto.getId())
                .name(dto.getName()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

  public static UserDTO toDTO(final User user) {
    return Optional.ofNullable(user)
        .map(entity ->
            UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }
}
