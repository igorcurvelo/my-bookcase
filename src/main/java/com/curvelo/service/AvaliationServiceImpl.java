package com.curvelo.service;

import com.curvelo.domain.model.Avaliation;
import com.curvelo.repository.AvaliationRepository;
import com.curvelo.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AvaliationServiceImpl implements AvaliationService {

  private final BookService bookService;
  private final AvaliationRepository avaliationRepository;
  private final UserRepository userRepository;

  public AvaliationServiceImpl(BookService bookService,
      AvaliationRepository avaliationRepository,
      UserRepository userRepository) {
    this.bookService = bookService;
    this.avaliationRepository = avaliationRepository;
    this.userRepository = userRepository;
  }

  @Override
  public Avaliation create(Integer bookId, Avaliation avaliation) {

    // TODO adicionar validacao de user

    var book = bookService.findOne(bookId);

    avaliation.setBook(book);

    return avaliationRepository.save(avaliation);
  }

  @Override
  public List<Avaliation> findByBook(Integer book) {
    return avaliationRepository.findByBookId(book);
  }

}
