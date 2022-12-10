package com.curvelo.adapter.input.restcontroller.dto;

import lombok.Builder;

@Builder
public record ReviewDto(Integer id,
                        Integer score,
                        String comment,
                        UserDto user) {
}