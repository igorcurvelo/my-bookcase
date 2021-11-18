package com.curvelo.adapter.rest.mapper;

import com.curvelo.api.dto.TotalReviewsDTO;
import com.curvelo.core.domain.TotalReviews;
import java.util.Optional;
import java.util.stream.Collectors;

public class TotalReviewsAdapterRest {

  private TotalReviewsAdapterRest() {}

  public static TotalReviewsDTO toDTO(final TotalReviews totalReviews) {
    return Optional.ofNullable(totalReviews)
        .map(domain ->
            TotalReviewsDTO.builder()
                .score(domain.getScore())
                .book(BookAdapterRest.toDTO(domain.getBook()))
                .comments(domain.getComments()
                    .stream()
                    .map(UserReviewAdapterRest::toDTO)
                    .collect(Collectors.toList()))
                .build()
        ).orElseThrow(IllegalArgumentException::new);
  }

}
