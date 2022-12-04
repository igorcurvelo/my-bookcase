package com.curvelo.adapter.mysql.mapper;

import com.curvelo.core.domain.User;
import com.curvelo.database.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserAdapterMysql {

  public User toDomain(final UserModel userModel) {
    return User.of(userModel.getId(), userModel.getName());
  }
}
