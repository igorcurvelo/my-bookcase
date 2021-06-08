package com.curvelo.repository;

import com.curvelo.domain.model.Avaliation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliationRepository extends JpaRepository<Avaliation, Integer> {

  Optional<Avaliation> findByBookId(Integer book);
}
