package com.curvelo.core.repository;

import com.curvelo.core.domain.ReviewDomain;
import java.util.List;

// todo verificar a necessidade
public interface ReviewDomainRepository {

  List<ReviewDomain> findByBookId(int bookId);
}
