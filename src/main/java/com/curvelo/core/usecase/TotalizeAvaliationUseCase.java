package com.curvelo.core.usecase;

import com.curvelo.core.domain.AvaliationDomain;
import com.curvelo.core.domain.BookDomain;
import com.curvelo.core.domain.TotalAvaliationDomain;
import com.curvelo.core.domain.UserAvaliationDomain;
import com.curvelo.core.repository.AvaliationDomainRepository;
import com.curvelo.core.repository.BookDomainRepository;
import java.util.Collections;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class TotalizeAvaliationUseCase {

  private final AvaliationDomainRepository avaliationDomainRepository;
  private final BookDomainRepository bookDomainRepository;

  public TotalizeAvaliationUseCase(
      AvaliationDomainRepository avaliationDomainRepository,
      BookDomainRepository bookDomainRepository) {
    this.avaliationDomainRepository = avaliationDomainRepository;
    this.bookDomainRepository = bookDomainRepository;
  }

  public TotalAvaliationDomain totalizeAvaliationsByBook(final int bookId) {
    var book = bookDomainRepository.findById(bookId);

    var avaliations = avaliationDomainRepository.findByBookId(
        bookId);

    if (avaliations.isEmpty()) {
      return defaultTotalize(book);
    }

    var average = avaliations.stream()
        .map(AvaliationDomain::getScore)
        .mapToDouble(Integer::doubleValue)
        .average()
        .orElse(0.0);

    var commentsByUser = avaliations.stream()
        .map(avaliation ->
            UserAvaliationDomain.builder()
                .comment(avaliation.getComment())
                .user(avaliation.getUser())
                .build())
        .collect(Collectors.toList());

    return TotalAvaliationDomain.builder()
        .book(book)
        .comments(commentsByUser)
        .score(average)
        .build();
  }

  private TotalAvaliationDomain defaultTotalize(final BookDomain book) {
    return TotalAvaliationDomain.builder()
        .book(book)
        .comments(Collections.emptyList())
        .score(0.0)
        .build();
  }
}
