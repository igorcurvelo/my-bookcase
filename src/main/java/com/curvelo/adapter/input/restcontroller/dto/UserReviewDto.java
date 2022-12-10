package com.curvelo.adapter.input.restcontroller.dto;

import lombok.Builder;

@Builder
public record UserReviewDto(String comment, UserDto user) {

}
