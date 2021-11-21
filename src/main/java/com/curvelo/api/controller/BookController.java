package com.curvelo.api.controller;

import com.curvelo.adapter.rest.mapper.BookAdapterRest;
import com.curvelo.adapter.rest.mapper.TotalReviewsAdapterRest;
import com.curvelo.api.dto.ReviewDTO;
import com.curvelo.api.dto.BookDTO;
import com.curvelo.api.dto.TotalReviewsDTO;
import com.curvelo.core.usecase.CalculateReviewsUseCase;
import com.curvelo.core.usecase.CreateBookUseCase;
import com.curvelo.mapper.ReviewMapper;
import com.curvelo.mapper.BookMapper;
import com.curvelo.service.ReviewService;
import com.curvelo.service.BookService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {

  private final BookService bookService;
  private final ReviewService reviewService;
  private final CalculateReviewsUseCase calculateReviewsUseCase;
  private final CreateBookUseCase createBookUseCase;
  private final BookAdapterRest bookAdapterRest;
  private final TotalReviewsAdapterRest totalReviewsAdapterRest;

  public BookController(
      final BookService bookService,
      final ReviewService reviewService,
      final CalculateReviewsUseCase calculateReviewsUseCase,
      final CreateBookUseCase createBookUseCase,
      final BookAdapterRest bookAdapterRest,
      final TotalReviewsAdapterRest totalReviewsAdapterRest) {
    this.bookService = bookService;
    this.reviewService = reviewService;
    this.calculateReviewsUseCase = calculateReviewsUseCase;
    this.createBookUseCase = createBookUseCase;
    this.bookAdapterRest = bookAdapterRest;
    this.totalReviewsAdapterRest = totalReviewsAdapterRest;
  }

  @GetMapping
  public List<BookDTO> get() {
    return bookService.findAll().stream()
        .map(BookMapper::toDTO)
        .collect(Collectors.toList());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookDTO post(@RequestBody final BookDTO input) {
    final var result = createBookUseCase.create(bookAdapterRest.toDomain(input));
    return bookAdapterRest.toDTO(result);
  }

  @PostMapping("/{bookId}/reviews")
  @ResponseStatus(HttpStatus.CREATED)
  public ReviewDTO postReview(@PathVariable Integer bookId, @RequestBody ReviewDTO body) {
    var result = reviewService.create(bookId, ReviewMapper.toEntity(body));
    return ReviewMapper.toDTO(result);
  }

  @GetMapping("/{bookId}/reviews")
  public List<ReviewDTO> getAllReview(@PathVariable Integer bookId) {
    var result = reviewService.findByBook(bookId);
    return result.stream().map(ReviewMapper::toDTO).collect(Collectors.toList());
  }

  @GetMapping("/{bookId}/reviews/calculate")
  public TotalReviewsDTO getAllCalculateReview(@PathVariable Integer bookId) {
    return totalReviewsAdapterRest.toDTO(calculateReviewsUseCase.calculateReviewsByBook(bookId));
  }

}
