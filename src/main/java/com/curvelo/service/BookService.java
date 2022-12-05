package com.curvelo.service;

import com.curvelo.database.model.BookModel;

public interface BookService {

  BookModel create(BookModel toEntity);

  BookModel findOne(Integer bookId);
}
