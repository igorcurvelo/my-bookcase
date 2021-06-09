package com.curvelo.service;

import com.curvelo.domain.model.Avaliation;
import com.curvelo.repository.AvaliationRepository;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AvaliationServiceImpl implements AvaliationService {

  private final BookService bookService;
  private final AvaliationRepository avaliationRepository;

  public AvaliationServiceImpl(BookService bookService,
      AvaliationRepository avaliationRepository) {
    this.bookService = bookService;
    this.avaliationRepository = avaliationRepository;
  }

  @Override
  public Avaliation create(Integer bookId, Avaliation avaliation) {

    var book = bookService.findOne(bookId);

    avaliation.setBook(book);

    return avaliationRepository.save(avaliation);
  }

  @Override
  public Avaliation findByBook(Integer book) {
    return avaliationRepository.findByBookId(book)
        .orElseThrow(() -> new EntityNotFoundException("This books doesn't have avaliation"));
  }

}
