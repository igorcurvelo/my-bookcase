package com.curvelo.adapter.mysql.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.curvelo.database.model.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserAdapterMysqlTest {

  @InjectMocks
  private UserAdapterMysql userAdapterMysql;

  @Test
  void shouldMapperUserModelToUserDomain() {
    var user = UserModel.builder()
        .id(99)
        .name("Igor")
        .build();

    var result = userAdapterMysql.toDomain(user);

    assertThat(result.getId()).isEqualTo(99);
    assertThat(result.getName()).isEqualTo("Igor");
  }

}