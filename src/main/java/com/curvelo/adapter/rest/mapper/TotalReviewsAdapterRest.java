package com.curvelo.adapter.rest.mapper;

import com.curvelo.adapter.input.restcontroller.dto.TotalReviewsDto;
import com.curvelo.core.domain.TotalReviews;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class TotalReviewsAdapterRest {

  private final BookAdapterRest bookAdapterRest;
  private final UserReviewAdapterRest userReviewAdapterRest;

  public TotalReviewsAdapterRest(
      final BookAdapterRest bookAdapterRest,
      final UserReviewAdapterRest userReviewAdapterRest) {
    this.bookAdapterRest = bookAdapterRest;
    this.userReviewAdapterRest = userReviewAdapterRest;
  }

  public TotalReviewsDto toDto(final TotalReviews totalReviews) {
    return Optional.ofNullable(totalReviews)
        .map(domain ->
            TotalReviewsDto.builder()
                .score(domain.getScore())
                .book(bookAdapterRest.toDto(domain.getBook()))
                .comments(domain.getComments()
                    .stream()
                    .map(userReviewAdapterRest::toDto)
                    .collect(Collectors.toList()))
                .build()
        ).orElseThrow(IllegalArgumentException::new);
  }

}
