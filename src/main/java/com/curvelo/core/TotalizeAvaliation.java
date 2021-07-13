package com.curvelo.core;

import com.curvelo.repository.AvaliationRepository;
import org.springframework.stereotype.Component;

@Component
public class TotalizeAvaliation {

  private final AvaliationRepository avaliationRepository;

  public TotalizeAvaliation(AvaliationRepository avaliationRepository) {
    this.avaliationRepository = avaliationRepository;
  }

  public void totalizeAvaliationsByBook(final int bookId) {
    avaliationRepository.findByBookId(bookId);


  }
}
