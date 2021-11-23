package com.curvelo.core.repository;

import com.curvelo.core.domain.Book;
import java.util.List;

public interface BookDomainRepository {

  Book findById(int bookId);

  Book save(Book book);

  List<Book> findAll();
}
