package com.curvelo.adapter.mysql.gateway;

import com.curvelo.core.domain.Isbn;
import com.curvelo.core.repository.IsbnDomainRepository;
import com.curvelo.database.repository.BookRepository;
import org.springframework.stereotype.Component;

@Component
public class IsbnMysqlRepository implements IsbnDomainRepository {

  private final BookRepository bookRepository;

  public IsbnMysqlRepository(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public boolean existsByIsbn(Isbn isbn) {
    return bookRepository.existsByIsbn(isbn.getValue());
  }
}
