package com.curvelo.api.controller;

import com.curvelo.api.dto.AvaliationDTO;
import com.curvelo.api.dto.BookDTO;
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

  public BookController(BookService bookService,
      AvaliationService avaliationService) {
    this.bookService = bookService;
    this.avaliationService = avaliationService;
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

  @PostMapping("/{bookId}/avaliation")
  @ResponseStatus(HttpStatus.CREATED)
  public AvaliationDTO postAvaliation(@PathVariable Integer bookId, @RequestBody AvaliationDTO body) {
    var result = avaliationService.create(bookId, AvaliationMapper.toEntity(body));
    return AvaliationMapper.toDTO(result);
  }

  @GetMapping("/{bookId}/avaliation")
  public AvaliationDTO postAvaliation(@PathVariable Integer bookId) {
    var result = avaliationService.findByBook(bookId);
    return AvaliationMapper.toDTO(result);
  }
}
