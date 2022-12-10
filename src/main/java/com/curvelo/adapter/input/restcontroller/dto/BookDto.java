package com.curvelo.adapter.input.restcontroller.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record BookDto(Integer id,
                      String title,
                      String isbn,
                      List<String> authors,
                      Integer numberOfPages) {

}
