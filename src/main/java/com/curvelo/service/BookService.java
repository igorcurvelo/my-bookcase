package com.curvelo.service;

import com.curvelo.domain.model.Book;
import java.util.List;

public interface BookService {

  List<Book> findAll();

  Book create(Book toEntity);

  Book findOne(Integer bookId);
}
