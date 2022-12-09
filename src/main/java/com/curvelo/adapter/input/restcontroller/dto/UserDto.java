package com.curvelo.adapter.input.restcontroller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserDto {

  private final Integer id;
  private final String name;

}
