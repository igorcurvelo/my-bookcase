package com.curvelo.mapper;

import com.curvelo.api.dto.AvaliationDTO;
import com.curvelo.database.model.AvaliationModel;
import java.util.Optional;

public class AvaliationMapper {

  private AvaliationMapper() {}

  public static AvaliationModel toEntity(final AvaliationDTO avaliationDTO) {
    return Optional.ofNullable(avaliationDTO)
        .map(dto ->
            AvaliationModel.builder()
                .id(dto.getId())
                .userModel(UserMapper.toEntity(dto.getUser()))
                .score(dto.getScore())
                .comment(dto.getComment()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

  public static AvaliationDTO toDTO(final AvaliationModel avaliationModel) {
    return Optional.ofNullable(avaliationModel)
        .map(entity ->
            AvaliationDTO.builder()
                .id(entity.getId())
                .book(BookMapper.toDTO(avaliationModel.getBookModel()))
                .user(UserMapper.toDTO(avaliationModel.getUserModel()))
                .score(entity.getScore())
                .comment(entity.getComment()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

}
