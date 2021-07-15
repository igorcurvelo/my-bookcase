package com.curvelo.api.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TotalAvaliationDTO {

  private final Double score;
  private final List<UserAvaliationDTO> comments;
  private final BookDTO book;

}
