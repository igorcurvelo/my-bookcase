package com.curvelo.api.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookAvaliationDTO {

  private final BookDTO book;

  private final Float averageScore;

  private final List<String> comments;

}
