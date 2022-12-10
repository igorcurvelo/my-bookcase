package com.curvelo.adapter.rest.mapper;

import com.curvelo.adapter.input.restcontroller.dto.UserDto;
import com.curvelo.core.domain.User;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserAdapterRest {

  public UserDto toDto(final User user) {
    return Optional.ofNullable(user)
        .map(entity ->
            UserDto.builder()
                .id(entity.getId())
                .name(entity.getName()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

  public User toDomain(UserDto user) {
    return User.of(
        user.id(), user.name()
    );
  }
}
