package com.curvelo.service;

import com.curvelo.database.model.ReviewModel;
import com.curvelo.database.repository.ReviewRepository;
import com.curvelo.database.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

  private final BookService bookService;
  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;

  public ReviewServiceImpl(BookService bookService,
      ReviewRepository reviewRepository,
      UserRepository userRepository) {
    this.bookService = bookService;
    this.reviewRepository = reviewRepository;
    this.userRepository = userRepository;
  }

  @Override
  public ReviewModel create(Integer bookId, ReviewModel reviewModel) {

    // TODO adicionar validacao de user

    var book = bookService.findOne(bookId);

    reviewModel.setBookModel(book);

    return reviewRepository.save(reviewModel);
  }

  @Override
  public List<ReviewModel> findByBook(Integer book) {
    return reviewRepository.findByBookId(book);
  }

}
