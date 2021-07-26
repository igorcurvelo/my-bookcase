package com.curvelo.service;

import com.curvelo.database.model.BookModel;
import java.util.List;

public interface BookService {

  List<BookModel> findAll();

  BookModel create(BookModel toEntity);

  BookModel findOne(Integer bookId);
}
