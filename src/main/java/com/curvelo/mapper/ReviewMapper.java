package com.curvelo.mapper;

import com.curvelo.api.dto.ReviewDTO;
import com.curvelo.database.model.ReviewModel;

public class ReviewMapper {

  private ReviewMapper() {}

  public static ReviewModel toEntity(final ReviewDTO reviewDTO) {
    return ReviewModel.builder()
                .id(reviewDTO.getId())
                .user(UserMapper.toEntity(reviewDTO.getUser()))
                .score(reviewDTO.getScore())
                .comment(reviewDTO.getComment()).build();
  }

  public static ReviewDTO toDTO(final ReviewModel reviewModel) {
    return ReviewDTO.builder()
                .id(reviewModel.getId())
                .book(BookMapper.toDTO(reviewModel.getBook()))
                .user(UserMapper.toDTO(reviewModel.getUser()))
                .score(reviewModel.getScore())
                .comment(reviewModel.getComment()).build();
  }

}
