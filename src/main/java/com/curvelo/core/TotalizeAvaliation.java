package com.curvelo.core;

import com.curvelo.api.dto.BookDTO;
import com.curvelo.api.dto.TotalAvaliationDTO;
import com.curvelo.domain.model.Avaliation;
import com.curvelo.mapper.BookMapper;
import com.curvelo.mapper.UserAvaliationMapper;
import com.curvelo.mapper.UserMapper;
import com.curvelo.repository.AvaliationRepository;
import com.curvelo.repository.BookRepository;
import java.util.Collections;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class TotalizeAvaliation {

  private final AvaliationRepository avaliationRepository;
  private final BookRepository bookRepository;

  public TotalizeAvaliation(AvaliationRepository avaliationRepository,
      BookRepository bookRepository) {
    this.avaliationRepository = avaliationRepository;
    this.bookRepository = bookRepository;
  }

  public TotalAvaliationDTO totalizeAvaliationsByBook(final int bookId) {
    var book = bookRepository.findById(bookId)
        .map(BookMapper::toDTO)
        .orElseThrow(() -> new EntityNotFoundException("Book not found"));

    var avaliations = avaliationRepository.findByBookId(
        bookId);

    if (avaliations.isEmpty()) {
      return defaultTotalize(book);
    }

    var average = avaliations.stream()
        .map(Avaliation::getScore)
        .mapToDouble(Integer::doubleValue)
        .average()
        .orElse(0.0);

    var commentsByUser = avaliations.stream()
        .map(avaliation ->
            UserAvaliationMapper.toDTO(avaliation.getComment(),
                UserMapper.toDTO(avaliation.getUser()))
        ).collect(Collectors.toList());

    return TotalAvaliationDTO.builder()
        .book(book)
        .comments(commentsByUser)
        .score(average)
        .build();
  }

  private TotalAvaliationDTO defaultTotalize(final BookDTO book) {
    return TotalAvaliationDTO.builder()
        .book(book)
        .comments(Collections.emptyList())
        .score(0.0)
        .build();
  }
}
