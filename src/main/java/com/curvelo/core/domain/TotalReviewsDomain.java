package com.curvelo.core.domain;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TotalReviewsDomain {

  private final Double score;
  private final List<UserReviewDomain> comments;
  private final BookDomain book;

}
