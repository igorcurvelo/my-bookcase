package com.curvelo.adapter.mysql.gateway;

import com.curvelo.adapter.mysql.mapper.BookAdapterMysql;
import com.curvelo.core.domain.BookDomain;
import com.curvelo.core.repository.BookDomainRepository;
import com.curvelo.database.repository.BookRepository;
import com.curvelo.database.repository.ReviewRepository;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class BookMysqlRepository implements BookDomainRepository {

  private final BookRepository bookRepository;
  private final ReviewRepository reviewRepository;

  public BookMysqlRepository(BookRepository bookRepository,
      ReviewRepository reviewRepository) {
    this.bookRepository = bookRepository;
    this.reviewRepository = reviewRepository;
  }

  @Override
  public BookDomain findById(int bookId) throws EntityNotFoundException {
    var reviewsModel = reviewRepository.findByBookId(bookId);

    return bookRepository.findById(bookId)
        .map(model -> BookAdapterMysql.toDomain(model, reviewsModel))
        .orElseThrow(() -> new EntityNotFoundException("Book not found"));
  }
}
