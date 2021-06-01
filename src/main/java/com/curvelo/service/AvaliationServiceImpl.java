package com.curvelo.service;

import com.curvelo.domain.model.Avaliation;
import com.curvelo.repository.AvaliationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliationServiceImpl implements AvaliationService {

  @Autowired
  private BookService bookService;

  @Autowired
  private AvaliationRepository avaliationRepository;

  @Override
  public Avaliation create(Integer bookId, Avaliation avaliation) {

    var book = bookService.findOne(bookId);

    avaliation.setBook(book);

    return avaliationRepository.save(avaliation);
  }

}
