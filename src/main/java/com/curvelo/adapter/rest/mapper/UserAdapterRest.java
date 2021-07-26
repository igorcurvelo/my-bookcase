package com.curvelo.adapter.rest.mapper;

import com.curvelo.api.dto.UserDTO;
import com.curvelo.core.domain.UserDomain;
import java.util.Optional;

public class UserAdapterRest {

  private UserAdapterRest() {}

  public static UserDTO toDTO(final UserDomain userDomain) {
    return Optional.ofNullable(userDomain)
        .map(entity ->
            UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }
}
