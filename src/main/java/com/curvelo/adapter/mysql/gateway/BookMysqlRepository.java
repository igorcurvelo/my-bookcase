package com.curvelo.adapter.mysql.gateway;

import com.curvelo.adapter.mysql.mapper.BookAdapterMysql;
import com.curvelo.core.domain.Book;
import com.curvelo.core.repository.BookDomainRepository;
import com.curvelo.database.repository.BookRepository;
import com.curvelo.database.repository.ReviewRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class BookMysqlRepository implements BookDomainRepository {

  private final BookRepository bookRepository;
  private final ReviewRepository reviewRepository;
  private final BookAdapterMysql bookAdapterMysql;

  public BookMysqlRepository(
      final BookRepository bookRepository,
      final ReviewRepository reviewRepository,
      final BookAdapterMysql bookAdapterMysql) {
    this.bookRepository = bookRepository;
    this.reviewRepository = reviewRepository;
    this.bookAdapterMysql = bookAdapterMysql;
  }

  @Override
  public Book findById(int bookId) {
    var reviewsModel = reviewRepository.findByBookId(bookId);

    return bookRepository.findById(bookId)
        .map(model -> bookAdapterMysql.toDomain(model, reviewsModel))
        .orElseThrow(() -> new EntityNotFoundException("Book not found"));
  }

  @Override
  public Book save(Book book) {
    var bookSaved = bookRepository.save(bookAdapterMysql.toModel(book));
    return bookAdapterMysql.toDomain(bookSaved);
  }

  @Override
  public List<Book> findAll() {
    return bookRepository.findAll()
        .stream().map(bookAdapterMysql::toDomain)
        .collect(Collectors.toList());
  }
}
