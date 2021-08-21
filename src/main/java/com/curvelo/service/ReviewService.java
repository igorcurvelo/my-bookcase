package com.curvelo.service;

import com.curvelo.database.model.ReviewModel;
import java.util.List;

public interface ReviewService {

  ReviewModel create(Integer bookId, ReviewModel reviewModel);

  List<ReviewModel> findByBook(Integer book);
}
