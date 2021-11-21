package com.curvelo.adapter.rest.mapper;

import com.curvelo.api.dto.UserReviewDTO;
import com.curvelo.core.domain.UserReview;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserReviewAdapterRest {

  private final UserAdapterRest userAdapterRest;

  public UserReviewAdapterRest(final UserAdapterRest userAdapterRest) {
    this.userAdapterRest = userAdapterRest;
  }

  public UserReviewDTO toDTO(final UserReview userReview) {
    return Optional.ofNullable(userReview)
        .map(entity ->
            UserReviewDTO.builder()
                .comment(userReview.getComment())
                .user(userAdapterRest.toDTO(userReview.getUser())).build()
        ).orElseThrow(IllegalArgumentException::new);
  }
}
