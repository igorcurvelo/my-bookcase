package com.curvelo.adapter.input.restcontroller.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record TotalReviewsDto(Double score,
                              List<UserReviewDto> comments,
                              BookDto book) {

}