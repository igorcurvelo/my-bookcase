package com.curvelo.repository;

import com.curvelo.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

  boolean existsByIsbn(String isbn);

}
