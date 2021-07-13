package com.curvelo.service;

import com.curvelo.domain.model.Avaliation;
import java.util.List;

public interface AvaliationService {

  Avaliation create(Integer bookId, Avaliation avaliation);

  List<Avaliation> findByBook(Integer book);
}
