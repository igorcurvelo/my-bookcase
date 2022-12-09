package com.curvelo.adapter.rest.mapper;

import com.curvelo.adapter.input.restcontroller.dto.UserReviewDto;
import com.curvelo.core.domain.UserReview;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserReviewAdapterRest {

  private final UserAdapterRest userAdapterRest;

  public UserReviewAdapterRest(final UserAdapterRest userAdapterRest) {
    this.userAdapterRest = userAdapterRest;
  }

  public UserReviewDto toDto(final UserReview userReview) {
    return Optional.ofNullable(userReview)
        .map(entity ->
            UserReviewDto.builder()
                .comment(userReview.getComment())
                .user(userAdapterRest.toDto(userReview.getUser())).build()
        ).orElseThrow(IllegalArgumentException::new);
  }
}
