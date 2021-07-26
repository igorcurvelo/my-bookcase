package com.curvelo.api.controller;

import com.curvelo.adapter.rest.mapper.TotalAvaliationAdapterRest;
import com.curvelo.api.dto.AvaliationDTO;
import com.curvelo.api.dto.BookDTO;
import com.curvelo.api.dto.TotalAvaliationDTO;
import com.curvelo.core.usecase.TotalizeAvaliationUseCase;
import com.curvelo.mapper.AvaliationMapper;
import com.curvelo.mapper.BookMapper;
import com.curvelo.service.AvaliationService;
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
  private final AvaliationService avaliationService;
  private final TotalizeAvaliationUseCase totalizeAvaliationUseCase;

  public BookController(BookService bookService,
      AvaliationService avaliationService,
      TotalizeAvaliationUseCase totalizeAvaliationUseCase) {
    this.bookService = bookService;
    this.avaliationService = avaliationService;
    this.totalizeAvaliationUseCase = totalizeAvaliationUseCase;
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
  public AvaliationDTO postAvaliation(@PathVariable Integer bookId, @RequestBody AvaliationDTO body) {
    var result = avaliationService.create(bookId, AvaliationMapper.toEntity(body));
    return AvaliationMapper.toDTO(result);
  }

  @GetMapping("/{bookId}/avaliations")
  public List<AvaliationDTO> getAllAvaliation(@PathVariable Integer bookId) {
    var result = avaliationService.findByBook(bookId);
    return result.stream().map(AvaliationMapper::toDTO).collect(Collectors.toList());
  }

  @GetMapping("/{bookId}/avaliations/totalize")
  public TotalAvaliationDTO getAllAvaliationTotalize(@PathVariable Integer bookId) {
    return TotalAvaliationAdapterRest.toDTO(totalizeAvaliationUseCase.totalizeAvaliationsByBook(bookId));
  }

}
