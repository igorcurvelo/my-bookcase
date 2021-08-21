package com.curvelo.mapper;

import com.curvelo.api.dto.UserDTO;
import com.curvelo.database.model.UserModel;
import java.util.Optional;

public class UserMapper {

  private UserMapper() {}

  public static UserModel toEntity(final UserDTO userDTO) {
    return Optional.ofNullable(userDTO)
        .map(dto ->
            UserModel.builder()
                .id(dto.getId())
                .name(dto.getName()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

  public static UserDTO toDTO(final UserModel userModel) {
    return Optional.ofNullable(userModel)
        .map(entity ->
            UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }
}
