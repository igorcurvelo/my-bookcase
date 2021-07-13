package com.curvelo.mapper;

import com.curvelo.api.dto.AvaliationDTO;
import com.curvelo.domain.model.Avaliation;
import java.util.Optional;

public class AvaliationMapper {

  private AvaliationMapper() {}

  public static Avaliation toEntity(final AvaliationDTO avaliationDTO) {
    return Optional.ofNullable(avaliationDTO)
        .map(dto ->
            Avaliation.builder()
                .id(dto.getId())
                .user(UserMapper.toEntity(dto.getUser()))
                .score(dto.getScore())
                .comment(dto.getComment()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

  public static AvaliationDTO toDTO(final Avaliation avaliation) {
    return Optional.ofNullable(avaliation)
        .map(entity ->
            AvaliationDTO.builder()
                .id(entity.getId())
                .book(BookMapper.toDTO(avaliation.getBook()))
                .user(UserMapper.toDTO(avaliation.getUser()))
                .score(entity.getScore())
                .comment(entity.getComment()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

}
