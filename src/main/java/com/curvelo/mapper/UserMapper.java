package com.curvelo.mapper;

import com.curvelo.adapter.input.restcontroller.dto.UserDTO;
import com.curvelo.database.model.UserModel;

public class UserMapper {

  private UserMapper() {}

  public static UserModel toEntity(final UserDTO userDTO) {
    return UserModel.builder()
                .id(userDTO.getId())
                .name(userDTO.getName()).build();
  }

  public static UserDTO toDTO(final UserModel userModel) {
    return UserDTO.builder()
                .id(userModel.getId())
                .name(userModel.getName()).build();
  }
}
