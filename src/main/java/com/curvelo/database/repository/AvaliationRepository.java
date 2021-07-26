package com.curvelo.database.repository;

import com.curvelo.database.model.AvaliationModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliationRepository extends JpaRepository<AvaliationModel, Integer> {

  List<AvaliationModel> findByBookId(Integer book);
}
