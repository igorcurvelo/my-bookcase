package com.curvelo.adapter.mysql.mapper;

import com.curvelo.core.domain.UserDomain;
import com.curvelo.database.model.UserModel;

public class UserAdapterMysql {

  private UserAdapterMysql() {}

  public static UserDomain toDomain(final UserModel userModel) {
    return UserDomain.builder()
                .id(userModel.getId())
                .name(userModel.getName()).build();
  }
}
