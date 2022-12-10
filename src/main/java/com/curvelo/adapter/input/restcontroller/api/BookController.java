package com.curvelo.adapter.input.restcontroller.api;

import com.curvelo.adapter.input.restcontroller.dto.BookDto;
import com.curvelo.adapter.input.restcontroller.dto.ReviewDto;
import com.curvelo.adapter.input.restcontroller.dto.TotalReviewsDto;
import com.curvelo.adapter.rest.mapper.BookAdapterRest;
import com.curvelo.adapter.rest.mapper.ReviewAdapterRest;
import com.curvelo.adapter.rest.mapper.TotalReviewsAdapterRest;
import com.curvelo.core.usecase.CalculateReviewsUseCase;
import com.curvelo.core.usecase.CreateBookUseCase;
import com.curvelo.core.usecase.GetterBookUseCase;
import com.curvelo.core.usecase.GetterReviewUseCase;
import com.curvelo.mapper.ReviewMapper;
import com.curvelo.service.ReviewService;
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

  private final ReviewService reviewService;
  private final CalculateReviewsUseCase calculateReviewsUseCase;
  private final CreateBookUseCase createBookUseCase;
  private final GetterBookUseCase getterBookUseCase;
  private final GetterReviewUseCase getterReviewUseCase;
  private final BookAdapterRest bookAdapterRest;
  private final ReviewAdapterRest reviewAdapterRest;
  private final TotalReviewsAdapterRest totalReviewsAdapterRest;

  public BookController(
      final ReviewService reviewService,
      final CalculateReviewsUseCase calculateReviewsUseCase,
      final CreateBookUseCase createBookUseCase,
      final GetterBookUseCase getterBookUseCase,
      final GetterReviewUseCase getterReviewUseCase,
      final BookAdapterRest bookAdapterRest,
      final ReviewAdapterRest reviewAdapterRest,
      final TotalReviewsAdapterRest totalReviewsAdapterRest) {
    this.reviewService = reviewService;
    this.calculateReviewsUseCase = calculateReviewsUseCase;
    this.createBookUseCase = createBookUseCase;
    this.getterBookUseCase = getterBookUseCase;
    this.getterReviewUseCase = getterReviewUseCase;
    this.bookAdapterRest = bookAdapterRest;
    this.reviewAdapterRest = reviewAdapterRest;
    this.totalReviewsAdapterRest = totalReviewsAdapterRest;
  }

  @GetMapping
  public List<BookDto> get() {
    return getterBookUseCase.findAll().stream()
        .map(bookAdapterRest::toDto)
        .collect(Collectors.toList());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookDto post(@RequestBody final BookDto input) {
    final var result = createBookUseCase.create(bookAdapterRest.toDomain(input));
    return bookAdapterRest.toDto(result);
  }

  @PostMapping("/{bookId}/reviews")
  @ResponseStatus(HttpStatus.CREATED)
  public ReviewDto postReview(
      @PathVariable final Integer bookId,
      @RequestBody final ReviewDto body) {
    final var result = reviewService.create(bookId, ReviewMapper.toEntity(body));
    return ReviewMapper.toDto(result);
  }

  @GetMapping("/{bookId}/reviews")
  public List<ReviewDto> getAllReview(@PathVariable Integer bookId) {
    final var result = getterReviewUseCase.findByBook(bookId);
    return result.stream().map(reviewAdapterRest::toDto).collect(Collectors.toList());
  }

  @GetMapping("/{bookId}/reviews/calculate")
  public TotalReviewsDto getAllCalculateReview(@PathVariable Integer bookId) {
    return totalReviewsAdapterRest.toDto(calculateReviewsUseCase.calculateReviewsByBook(bookId));
  }

}
