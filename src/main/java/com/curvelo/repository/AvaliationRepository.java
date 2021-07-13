package com.curvelo.repository;

import com.curvelo.domain.model.Avaliation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliationRepository extends JpaRepository<Avaliation, Integer> {

  List<Avaliation> findByBookId(Integer book);
}
