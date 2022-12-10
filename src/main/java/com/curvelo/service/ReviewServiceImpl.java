package com.curvelo.service;

import com.curvelo.database.model.ReviewModel;
import com.curvelo.database.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

  private final BookService bookService;
  private final ReviewRepository reviewRepository;

  public ReviewServiceImpl(BookService bookService,
      ReviewRepository reviewRepository) {
    this.bookService = bookService;
    this.reviewRepository = reviewRepository;
  }

  @Override
  public ReviewModel create(Integer bookId, ReviewModel reviewModel) {
    var book = bookService.findOne(bookId);

    reviewModel.setBook(book);

    return reviewRepository.save(reviewModel);
  }

}
