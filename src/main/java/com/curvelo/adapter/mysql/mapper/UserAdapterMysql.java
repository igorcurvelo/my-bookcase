package com.curvelo.adapter.mysql.mapper;

import com.curvelo.core.domain.UserDomain;
import com.curvelo.database.model.UserModel;
import java.util.Optional;

public class UserAdapterMysql {

  private UserAdapterMysql() {}

  public static UserModel toEntity(final UserDomain userDomain) {
    return Optional.ofNullable(userDomain)
        .map(dto ->
            UserModel.builder()
                .id(dto.getId())
                .name(dto.getName()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

  public static UserDomain toDomain(final UserModel userModel) {
    return Optional.ofNullable(userModel)
        .map(entity ->
            UserDomain.builder()
                .id(entity.getId())
                .name(entity.getName()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }
}
