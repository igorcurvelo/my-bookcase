package com.curvelo.adapter.mysql.gateway;

import com.curvelo.adapter.mysql.mapper.BookAdapterMysql;
import com.curvelo.core.domain.BookDomain;
import com.curvelo.core.repository.BookDomainRepository;
import com.curvelo.database.repository.BookRepository;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class BookMysqlRepository implements BookDomainRepository {

  private final BookRepository bookRepository;

  public BookMysqlRepository(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public BookDomain findById(int bookId) throws EntityNotFoundException {
    return bookRepository.findById(bookId)
        .map(BookAdapterMysql::toDomain)
        .orElseThrow(() -> new EntityNotFoundException("Book not found"));
  }
}
