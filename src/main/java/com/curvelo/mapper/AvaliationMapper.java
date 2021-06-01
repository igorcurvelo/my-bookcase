package com.curvelo.mapper;

import com.curvelo.api.dto.AvaliationDTO;
import com.curvelo.domain.model.Avaliation;
import java.util.Optional;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AvaliationMapper {

  public static Avaliation toEntity(final AvaliationDTO avaliationDTO) {
    return Optional.ofNullable(avaliationDTO)
        .map(dto ->
            Avaliation.builder()
                .id(dto.getId())
                .score(dto.getScore())
                .comment(dto.getComment()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

  public static AvaliationDTO toDTO(final Avaliation avaliation) {
    return Optional.ofNullable(avaliation)
        .map(entity ->
            AvaliationDTO.builder()
                .id(entity.getId())
                .score(entity.getScore())
                .comment(entity.getComment()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

}
