package com.curvelo.core.repository;

import com.curvelo.core.domain.BookDomain;
import javax.persistence.EntityNotFoundException;

public interface BookDomainRepository {

  BookDomain findById(int bookId) throws EntityNotFoundException;
}
