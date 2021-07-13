package com.curvelo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.curvelo.domain.model.Avaliation;
import com.curvelo.domain.model.Book;
import com.curvelo.domain.model.User;
import com.curvelo.repository.AvaliationRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AvaliationServiceImplTest {

  @Mock
  private AvaliationRepository avaliationRepository;

  @Mock
  private BookService bookService;

  @InjectMocks
  private AvaliationServiceImpl avaliationService;

  @Test
  void shouldCreateAAvaliationWithSuccess() {

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
  void shouldReturnManyAvaliationWithSuccess() {

    var book = Book.builder()
        .id(2)
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

    var user1 = User.builder()
        .id(99)
        .name("Igor")
        .build();

    var avaliation1 = Avaliation.builder()
        .id(3)
        .comment("excelente leitura")
        .score(4)
        .book(book)
        .user(user1)
        .build();

    var user2 = User.builder()
        .id(98)
        .name("Igor C")
        .build();

    var avaliation2 = Avaliation.builder()
        .id(4)
        .comment("boa leitura")
        .score(3)
        .book(book)
        .user(user2)
        .build();

    when(avaliationRepository.findByBookId(2))
        .thenReturn(Lists.list(avaliation1, avaliation2));

    var result = avaliationService.findByBook(2);

    assertThat(result).hasSize(2);
    assertThat(result.get(0).getId()).isEqualTo(3);
    assertThat(result.get(0).getComment()).isEqualTo("excelente leitura");
    assertThat(result.get(0).getScore()).isEqualTo(4);
    assertThat(result.get(0).getUser().getId()).isEqualTo(99);
    assertThat(result.get(0).getBook().getId()).isEqualTo(2);
    assertThat(result.get(0).getBook().getAuthor()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.get(0).getBook().getTitle()).isEqualTo("Hobbit");
    assertThat(result.get(0).getBook().getNumberOfPages()).isEqualTo(250);
    assertThat(result.get(0).getBook().getIsbn()).isEqualTo("123456789");

    assertThat(result.get(1).getId()).isEqualTo(4);
    assertThat(result.get(1).getComment()).isEqualTo("boa leitura");
    assertThat(result.get(1).getScore()).isEqualTo(3);
    assertThat(result.get(1).getUser().getId()).isEqualTo(98);
    assertThat(result.get(1).getBook().getId()).isEqualTo(2);
    assertThat(result.get(1).getBook().getAuthor()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.get(1).getBook().getTitle()).isEqualTo("Hobbit");
    assertThat(result.get(1).getBook().getNumberOfPages()).isEqualTo(250);
    assertThat(result.get(1).getBook().getIsbn()).isEqualTo("123456789");

    verify(avaliationRepository, times(1)).findByBookId(2);
  }
}