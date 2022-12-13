package com.curvelo.adapter.mysql.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.curvelo.core.domain.User;
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
    final var user = UserModel.builder()
        .id(99)
        .name("Igor")
        .build();

    final var result = userAdapterMysql.toDomain(user);

    assertThat(result.getId()).isEqualTo(99);
    assertThat(result.getName()).isEqualTo("Igor");
  }

  @Test
  void shouldMapperUserDomainToUserModel() {
    final var user = User.of(
        99, "Igor"
    );

    final var result = userAdapterMysql.toModel(user);

    assertThat(result.getId()).isEqualTo(99);
    assertThat(result.getName()).isEqualTo("Igor");
  }

}