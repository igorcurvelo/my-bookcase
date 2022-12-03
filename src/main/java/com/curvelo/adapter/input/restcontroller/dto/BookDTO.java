package com.curvelo.adapter.input.restcontroller.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class BookDTO {

  private final Integer id;
  private final String title;
  private final String isbn;
  private final List<String> authors;
  private final Integer numberOfPages;

}
