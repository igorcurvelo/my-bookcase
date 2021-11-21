package com.curvelo.adapter.rest.mapper;

import com.curvelo.api.dto.TotalReviewsDTO;
import com.curvelo.core.domain.TotalReviews;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TotalReviewsAdapterRest {

  private final BookAdapterRest bookAdapterRest;
  private final UserReviewAdapterRest userReviewAdapterRest;

  public TotalReviewsAdapterRest(BookAdapterRest bookAdapterRest,
      UserReviewAdapterRest userReviewAdapterRest) {
    this.bookAdapterRest = bookAdapterRest;
    this.userReviewAdapterRest = userReviewAdapterRest;
  }

  public TotalReviewsDTO toDTO(final TotalReviews totalReviews) {
    return Optional.ofNullable(totalReviews)
        .map(domain ->
            TotalReviewsDTO.builder()
                .score(domain.getScore())
                .book(bookAdapterRest.toDTO(domain.getBook()))
                .comments(domain.getComments()
                    .stream()
                    .map(userReviewAdapterRest::toDTO)
                    .collect(Collectors.toList()))
                .build()
        ).orElseThrow(IllegalArgumentException::new);
  }

}
