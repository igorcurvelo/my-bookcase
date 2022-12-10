package com.curvelo.mapper;

import com.curvelo.adapter.input.restcontroller.dto.ReviewDto;
import com.curvelo.database.model.ReviewModel;

public class ReviewMapper {

  private ReviewMapper() {}

  public static ReviewModel toEntity(final ReviewDto reviewDto) {
    return ReviewModel.builder()
                .id(reviewDto.id())
                .user(UserMapper.toEntity(reviewDto.user()))
                .score(reviewDto.score())
                .comment(reviewDto.comment()).build();
  }

  public static ReviewDto toDto(final ReviewModel reviewModel) {
    return ReviewDto.builder()
                .id(reviewModel.getId())
                .user(UserMapper.toDto(reviewModel.getUser()))
                .score(reviewModel.getScore())
                .comment(reviewModel.getComment()).build();
  }

}
