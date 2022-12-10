package com.curvelo.adapter.rest.mapper;

import com.curvelo.adapter.input.restcontroller.dto.ReviewDto;
import com.curvelo.core.domain.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewAdapterRest {

  private final UserAdapterRest userAdapterRest;

  public ReviewAdapterRest(final UserAdapterRest userAdapterRest) {
    this.userAdapterRest = userAdapterRest;
  }

  public ReviewDto toDto(final Review review) {
    return ReviewDto.builder()
        .id(review.getId())
        .score(review.getScore())
        .comment(review.getComment())
        .user(userAdapterRest.toDto(review.getUser()))
        .build();
  }

  public Review toDomain(ReviewDto dto) {
    return Review.of(
        dto.id(),
        dto.score(),
        dto.comment(),
        userAdapterRest.toDomain(dto.user())
    );
  }
}
