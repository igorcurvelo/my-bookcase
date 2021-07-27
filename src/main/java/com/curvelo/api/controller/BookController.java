package com.curvelo.api.controller;

import com.curvelo.adapter.rest.mapper.TotalReviewsAdapterRest;
import com.curvelo.api.dto.ReviewDTO;
import com.curvelo.api.dto.BookDTO;
import com.curvelo.api.dto.TotalReviewsDTO;
import com.curvelo.core.usecase.CalculateReviewsUseCase;
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

  public BookController(BookService bookService,
      ReviewService reviewService,
      CalculateReviewsUseCase calculateReviewsUseCase) {
    this.bookService = bookService;
    this.reviewService = reviewService;
    this.calculateReviewsUseCase = calculateReviewsUseCase;
  }

  @GetMapping
  public List<BookDTO> get() {
    return bookService.findAll().stream()
        .map(BookMapper::toDTO)
        .collect(Collectors.toList());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookDTO post(@RequestBody BookDTO input) {
    var result = bookService.create(BookMapper.toEntity(input));
    return BookMapper.toDTO(result);
  }

  @PostMapping("/{bookId}/avaliations")
  @ResponseStatus(HttpStatus.CREATED)
  public ReviewDTO postAvaliation(@PathVariable Integer bookId, @RequestBody ReviewDTO body) {
    var result = reviewService.create(bookId, ReviewMapper.toEntity(body));
    return ReviewMapper.toDTO(result);
  }

  @GetMapping("/{bookId}/avaliations")
  public List<ReviewDTO> getAllAvaliation(@PathVariable Integer bookId) {
    var result = reviewService.findByBook(bookId);
    return result.stream().map(ReviewMapper::toDTO).collect(Collectors.toList());
  }

  @GetMapping("/{bookId}/avaliations/totalize")
  public TotalReviewsDTO getAllAvaliationTotalize(@PathVariable Integer bookId) {
    return TotalReviewsAdapterRest.toDTO(calculateReviewsUseCase.calculateReviewsByBook(bookId));
  }

}
