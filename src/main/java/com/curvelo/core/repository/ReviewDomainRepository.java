package com.curvelo.core.repository;

import com.curvelo.core.domain.Review;
import java.util.List;

public interface ReviewDomainRepository {

  List<Review> findByBookId(int bookId);
}
