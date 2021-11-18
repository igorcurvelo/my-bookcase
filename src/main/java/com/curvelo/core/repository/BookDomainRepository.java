package com.curvelo.core.repository;

import com.curvelo.core.domain.Book;

public interface BookDomainRepository {

  Book findById(int bookId);

  Book save(Book book);
}
