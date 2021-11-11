package com.curvelo.core.usecase;

import com.curvelo.core.domain.BookDomain;
import com.curvelo.core.repository.BookDomainRepository;
import com.curvelo.core.repository.IsbnDomainRepository;
import javax.persistence.EntityExistsException;
import org.springframework.stereotype.Component;

@Component
public class CreateBookUseCase {

  private final BookDomainRepository bookDomainRepository;
  private final IsbnDomainRepository isbnDomainRepository;

  public CreateBookUseCase(BookDomainRepository bookDomainRepository,
      IsbnDomainRepository isbnDomainRepository) {
    this.bookDomainRepository = bookDomainRepository;
    this.isbnDomainRepository = isbnDomainRepository;
  }

  public BookDomain create(BookDomain book) {
    if(isbnDomainRepository.existsByIsbn(book.getIsbn())) {
      throw new EntityExistsException("book already registered");
    }

    return bookDomainRepository.save(book);
  }

}
