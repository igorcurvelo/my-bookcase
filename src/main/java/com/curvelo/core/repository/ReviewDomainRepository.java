package com.curvelo.core.repository;

import com.curvelo.core.domain.ReviewDomain;
import java.util.List;

public interface ReviewDomainRepository {

  List<ReviewDomain> findByBookId(int bookId);
}
