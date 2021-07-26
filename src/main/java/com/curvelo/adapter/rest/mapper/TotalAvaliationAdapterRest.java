package com.curvelo.adapter.rest.mapper;

import com.curvelo.api.dto.TotalAvaliationDTO;
import com.curvelo.core.domain.TotalAvaliationDomain;
import java.util.Optional;
import java.util.stream.Collectors;

public class TotalAvaliationAdapterRest {

  private TotalAvaliationAdapterRest() {}

  public static TotalAvaliationDTO toDTO(final TotalAvaliationDomain totalAvaliationDomain) {
    return Optional.ofNullable(totalAvaliationDomain)
        .map(domain ->
            TotalAvaliationDTO.builder()
                .score(domain.getScore())
                .book(BookAdapterRest.toDTO(domain.getBook()))
                .comments(domain.getComments()
                    .stream()
                    .map(UserAvaliationAdapterRest::toDTO)
                    .collect(Collectors.toList()))
                .build()
        ).orElseThrow(IllegalArgumentException::new);
  }

}
