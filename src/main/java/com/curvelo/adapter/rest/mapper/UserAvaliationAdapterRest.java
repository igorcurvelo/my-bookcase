package com.curvelo.adapter.rest.mapper;

import com.curvelo.api.dto.UserAvaliationDTO;
import com.curvelo.core.domain.UserAvaliationDomain;
import java.util.Optional;

public class UserAvaliationAdapterRest {

  private UserAvaliationAdapterRest() {}

  public static UserAvaliationDTO toDTO(final UserAvaliationDomain userAvaliationDomain) {
    return Optional.ofNullable(userAvaliationDomain)
        .map(entity ->
            UserAvaliationDTO.builder()
                .comment(userAvaliationDomain.getComment())
                .user(UserAdapterRest.toDTO(userAvaliationDomain.getUser())).build()
        ).orElseThrow(IllegalArgumentException::new);
  }
}
