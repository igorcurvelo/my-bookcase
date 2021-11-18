package com.curvelo.adapter.mysql.mapper;

import com.curvelo.core.domain.User;
import com.curvelo.database.model.UserModel;

public class UserAdapterMysql {

  private UserAdapterMysql() {}

  public static User toDomain(final UserModel userModel) {
    return User.of(userModel.getId(), userModel.getName());
  }
}
