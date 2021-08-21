package com.curvelo.core.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookDomain {

  private final Integer id;
  private final String title;
  private final String isbn;
  private final String author;
  private final Integer numberOfPages;

}
