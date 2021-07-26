package com.curvelo.core.domain;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TotalAvaliationDomain {

  private final Double score;
  private final List<UserAvaliationDomain> comments;
  private final BookDomain book;

}
