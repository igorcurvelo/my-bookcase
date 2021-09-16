package com.curvelo.core.repository;

import com.curvelo.core.domain.BookDomain;

public interface BookDomainRepository {

  BookDomain findById(int bookId);
}
