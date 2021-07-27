package com.curvelo.mapper;

import com.curvelo.api.dto.ReviewDTO;
import com.curvelo.database.model.ReviewModel;
import java.util.Optional;

public class ReviewMapper {

  private ReviewMapper() {}

  public static ReviewModel toEntity(final ReviewDTO reviewDTO) {
    return Optional.ofNullable(reviewDTO)
        .map(dto ->
            ReviewModel.builder()
                .id(dto.getId())
                .userModel(UserMapper.toEntity(dto.getUser()))
                .score(dto.getScore())
                .comment(dto.getComment()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

  public static ReviewDTO toDTO(final ReviewModel reviewModel) {
    return Optional.ofNullable(reviewModel)
        .map(entity ->
            ReviewDTO.builder()
                .id(entity.getId())
                .book(BookMapper.toDTO(reviewModel.getBookModel()))
                .user(UserMapper.toDTO(reviewModel.getUserModel()))
                .score(entity.getScore())
                .comment(entity.getComment()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

}
