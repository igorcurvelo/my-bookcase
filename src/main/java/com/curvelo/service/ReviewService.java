package com.curvelo.service;

import com.curvelo.database.model.ReviewModel;

public interface ReviewService {

  ReviewModel create(Integer bookId, ReviewModel reviewModel);
}
