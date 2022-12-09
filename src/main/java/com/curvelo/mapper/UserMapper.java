package com.curvelo.mapper;

import com.curvelo.adapter.input.restcontroller.dto.UserDto;
import com.curvelo.database.model.UserModel;

public class UserMapper {

  private UserMapper() {}

  public static UserModel toEntity(final UserDto userDto) {
    return UserModel.builder()
                .id(userDto.getId())
                .name(userDto.getName()).build();
  }

  public static UserDto toDto(final UserModel userModel) {
    return UserDto.builder()
                .id(userModel.getId())
                .name(userModel.getName()).build();
  }
}
