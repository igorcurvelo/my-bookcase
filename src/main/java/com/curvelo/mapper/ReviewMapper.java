package com.curvelo.mapper;

import com.curvelo.adapter.input.restcontroller.dto.ReviewDto;
import com.curvelo.database.model.ReviewModel;

public class ReviewMapper {

  private ReviewMapper() {}

  public static ReviewModel toEntity(final ReviewDto reviewDto) {
    return ReviewModel.builder()
                .id(reviewDto.getId())
                .user(UserMapper.toEntity(reviewDto.getUser()))
                .score(reviewDto.getScore())
                .comment(reviewDto.getComment()).build();
  }

  public static ReviewDto toDto(final ReviewModel reviewModel) {
    return ReviewDto.builder()
                .id(reviewModel.getId())
                .book(BookMapper.toDto(reviewModel.getBook()))
                .user(UserMapper.toDto(reviewModel.getUser()))
                .score(reviewModel.getScore())
                .comment(reviewModel.getComment()).build();
  }

}
