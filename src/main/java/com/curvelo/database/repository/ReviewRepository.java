package com.curvelo.database.repository;

import com.curvelo.database.model.ReviewModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewModel, Integer> {

  List<ReviewModel> findByBookId(Integer book);
}
