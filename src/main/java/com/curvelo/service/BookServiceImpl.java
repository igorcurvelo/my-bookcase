package com.curvelo.service;

import com.curvelo.database.model.BookModel;
import com.curvelo.database.repository.BookRepository;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  public BookServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public BookModel create(BookModel entity) {
    if (bookRepository.existsByIsbn(entity.getIsbn())) {
      throw new EntityExistsException("book already registered");
    }

    return bookRepository.save(entity);
  }

  @Override
  public BookModel findOne(Integer bookId) {
    return bookRepository.findById(bookId)
        .orElseThrow(() -> new EntityNotFoundException("Book not found"));
  }
}
