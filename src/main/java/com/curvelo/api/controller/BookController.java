package com.curvelo.api.controller;

import com.curvelo.api.dto.BookDTO;
import com.curvelo.mapper.BookMapper;
import com.curvelo.service.BookService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {

  private final BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
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


}
