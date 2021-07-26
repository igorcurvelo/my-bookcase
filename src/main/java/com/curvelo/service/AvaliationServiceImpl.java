package com.curvelo.service;

import com.curvelo.database.model.AvaliationModel;
import com.curvelo.database.repository.AvaliationRepository;
import com.curvelo.database.repository.UserRepository;
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
  public AvaliationModel create(Integer bookId, AvaliationModel avaliationModel) {

    // TODO adicionar validacao de user

    var book = bookService.findOne(bookId);

    avaliationModel.setBookModel(book);

    return avaliationRepository.save(avaliationModel);
  }

  @Override
  public List<AvaliationModel> findByBook(Integer book) {
    return avaliationRepository.findByBookId(book);
  }

}
