package com.curvelo.core.usecase;

import com.curvelo.core.domain.Book;
import com.curvelo.core.repository.BookDomainRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetterBookUseCase {

  private final BookDomainRepository bookDomainRepository;

  public GetterBookUseCase(BookDomainRepository bookDomainRepository) {
    this.bookDomainRepository = bookDomainRepository;
  }

  public List<Book> findAll() {
    return bookDomainRepository.findAll();
  }
}
