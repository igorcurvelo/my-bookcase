package com.curvelo.database.repository;

import com.curvelo.database.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookModel, Integer> {

  boolean existsByIsbn(String isbn);

}
