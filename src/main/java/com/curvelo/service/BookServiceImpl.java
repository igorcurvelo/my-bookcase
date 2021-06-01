package com.curvelo.service;

import com.curvelo.model.Book;
import com.curvelo.repository.BookRepository;
import java.util.List;
import javax.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  @Autowired
  public BookServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  @Override
  public Book create(Book entity) {
    if(bookRepository.existsByIsbn(entity.getIsbn())) {
      throw new EntityExistsException("book already registered");
    }

    return bookRepository.save(entity);
  }
}
