package com.curvelo.adapter.mysql.mapper;

import com.curvelo.core.domain.UserDomain;
import com.curvelo.database.model.UserModel;
import java.util.Optional;

public class UserAdapterMysql {

  private UserAdapterMysql() {}

  public static UserDomain toDomain(final UserModel userModel) {
    return Optional.ofNullable(userModel)
        .map(entity ->
            UserDomain.builder()
                .id(entity.getId())
                .name(entity.getName()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }
}
