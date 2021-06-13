package com.curvelo.service;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.curvelo.domain.model.Avaliation;
import com.curvelo.domain.model.Book;
import com.curvelo.repository.AvaliationRepository;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AvaliationServiceImplTest {

  @Mock
  private AvaliationRepository avaliationRepository;

  @Mock
  private BookService bookService;

  @InjectMocks
  private AvaliationServiceImpl avaliationService;

  @Test
  public void shouldCreateAAvaliationWithSuccess() {

    var book = Book.builder()
        .id(1)
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

    when(bookService.findOne(1)).thenReturn(book);
    when(avaliationRepository.save(any(Avaliation.class)))
        .thenReturn(Avaliation.builder()
          .id(2)
          .book(book)
          .comment("Um bom livro")
          .score(3)
          .build());

    var result = avaliationService.create(1,
        Avaliation.builder()
          .comment("Um bom livro")
          .score(3)
          .build());

    assertThat(result.getId()).isNotNull();
    assertThat(result.getComment()).isEqualTo("Um bom livro");
    assertThat(result.getScore()).isEqualTo(3);
    assertThat(result.getBook().getId()).isEqualTo(1);
    assertThat(result.getBook().getAuthor()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getBook().getTitle()).isEqualTo("Hobbit");
    assertThat(result.getBook().getNumberOfPages()).isEqualTo(250);
    assertThat(result.getBook().getIsbn()).isEqualTo("123456789");

    verify(bookService, times(1)).findOne(1);
    verify(avaliationRepository, times(1)).save(any(Avaliation.class));
  }

  @Test
  public void shouldReturnAAvaliationWithSuccess() {

    var book = Book.builder()
        .id(2)
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

    var avaliation = Avaliation.builder()
        .id(3)
        .comment("excelente leitura")
        .score(4)
        .book(book)
        .build();

    when(avaliationRepository.findByBookId(2))
        .thenReturn(of(avaliation));

    var result = avaliationService.findByBook(2);

    assertThat(result.getId()).isEqualTo(3);
    assertThat(result.getComment()).isEqualTo("excelente leitura");
    assertThat(result.getScore()).isEqualTo(4);
    assertThat(result.getBook().getId()).isEqualTo(2);
    assertThat(result.getBook().getAuthor()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getBook().getTitle()).isEqualTo("Hobbit");
    assertThat(result.getBook().getNumberOfPages()).isEqualTo(250);
    assertThat(result.getBook().getIsbn()).isEqualTo("123456789");

    verify(avaliationRepository, times(1)).findByBookId(2);
  }

  @Test
  public void shouldReturnEntityNotFoundExceptionWhenNotExistAAvaliationByBook() {

    when(avaliationRepository.findByBookId(3)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> avaliationService.findByBook(3))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("This books doesn't have avaliation");

    verify(avaliationRepository, times(1)).findByBookId(3);
  }
}