package com.curvelo.adapter.mysql.gateway;

import com.curvelo.adapter.mysql.mapper.ReviewAdapterMysql;
import com.curvelo.core.domain.Review;
import com.curvelo.core.repository.ReviewDomainRepository;
import com.curvelo.database.model.BookModel;
import com.curvelo.database.model.ReviewModel;
import com.curvelo.database.repository.BookRepository;
import com.curvelo.database.repository.ReviewRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ReviewMysqlRepository implements ReviewDomainRepository {

  private final ReviewRepository reviewRepository;
  private final BookRepository bookRepository;
  private final ReviewAdapterMysql reviewAdapterMysql;

  public ReviewMysqlRepository(
      final ReviewRepository reviewRepository,
      final BookRepository bookRepository,
      final ReviewAdapterMysql reviewAdapterMysql) {
    this.reviewRepository = reviewRepository;
    this.bookRepository = bookRepository;
    this.reviewAdapterMysql = reviewAdapterMysql;
  }

  @Override
  public List<Review> findByBookId(int bookId) {
    return reviewRepository.findByBookId(bookId)
        .stream().map(reviewAdapterMysql::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Review save(Integer bookId, Review review) {
    final BookModel book = bookRepository.findById(bookId)
        .orElseThrow(() -> new EntityNotFoundException("Book not found"));

    final var reviewModel = reviewAdapterMysql.toModel(review);

    reviewModel.setBook(book);

    reviewRepository.save(reviewModel);

    return reviewAdapterMysql.toDomain(reviewModel);
  }
}
