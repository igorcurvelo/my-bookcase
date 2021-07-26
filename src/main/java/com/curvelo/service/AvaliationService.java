package com.curvelo.service;

import com.curvelo.database.model.AvaliationModel;
import java.util.List;

public interface AvaliationService {

  AvaliationModel create(Integer bookId, AvaliationModel avaliationModel);

  List<AvaliationModel> findByBook(Integer book);
}
