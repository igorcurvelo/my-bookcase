package com.curvelo.core.repository;

import com.curvelo.core.domain.AvaliationDomain;
import java.util.List;

public interface AvaliationDomainRepository {

  List<AvaliationDomain> findByBookId(int bookId);
}
