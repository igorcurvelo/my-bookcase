package com.curvelo.core.repository;

import com.curvelo.core.domain.Isbn;

public interface IsbnDomainRepository {

  boolean existsByIsbn(Isbn isbn);
}
