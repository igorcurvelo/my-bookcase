package com.curvelo.adapter.rest.mapper;

import com.curvelo.api.dto.UserDTO;
import com.curvelo.core.domain.User;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserAdapterRest {

  public UserDTO toDTO(final User user) {
    return Optional.ofNullable(user)
        .map(entity ->
            UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }
}
