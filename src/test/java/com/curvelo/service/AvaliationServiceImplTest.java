package com.curvelo.service;

import static java.util.Optional.of;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.curvelo.domain.model.Avaliation;
import com.curvelo.domain.model.Book;
import com.curvelo.repository.AvaliationRepository;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
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

    assertNotNull(result.getId());
    assertEquals("Um bom livro", result.getComment());
    assertEquals(Integer.valueOf(3), result.getScore());
    assertEquals(Integer.valueOf(1), result.getBook().getId());
    assertEquals("J.R.R. Tolkien", result.getBook().getAuthor());
    assertEquals("Hobbit", result.getBook().getTitle());
    assertEquals(Integer.valueOf(250), result.getBook().getNumberOfPages());
    assertEquals("123456789", result.getBook().getIsbn());

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

    assertEquals(Integer.valueOf(3), result.getId());
    assertEquals("excelente leitura", result.getComment());
    assertEquals(Integer.valueOf(4), result.getScore());
    assertEquals(Integer.valueOf(2), result.getBook().getId());
    assertEquals("J.R.R. Tolkien", result.getBook().getAuthor());
    assertEquals("Hobbit", result.getBook().getTitle());
    assertEquals(Integer.valueOf(250), result.getBook().getNumberOfPages());
    assertEquals("123456789", result.getBook().getIsbn());

    verify(avaliationRepository, times(1)).findByBookId(2);
  }

  @Test(expected = EntityNotFoundException.class)
  public void shouldReturnEntityNotFoundExceptionWhenNotExistAAvaliationByBook() {

    when(avaliationRepository.findByBookId(3)).thenReturn(Optional.empty());

    avaliationService.findByBook(3);

    verify(avaliationRepository, times(1)).findByBookId(3);
  }
}