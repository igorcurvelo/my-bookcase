package com.curvelo.adapter.mysql.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.curvelo.database.model.UserModel;
import com.curvelo.mapper.UserMapper;
import org.junit.jupiter.api.Test;

class UserAdapterMysqlTest {

  @Test
  void shouldMapperUserModelToUserDomain() {
    var user = UserModel.builder()
        .id(99)
        .name("Igor")
        .build();

    var result = UserAdapterMysql.toDomain(user);

    assertThat(result.getId()).isEqualTo(99);
    assertThat(result.getName()).isEqualTo("Igor");
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenModelIsNull() {
    assertThatThrownBy(() -> UserAdapterMysql.toDomain(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

}