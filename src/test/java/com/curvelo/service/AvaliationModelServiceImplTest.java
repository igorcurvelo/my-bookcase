package com.curvelo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.curvelo.database.model.AvaliationModel;
import com.curvelo.database.model.BookModel;
import com.curvelo.database.model.UserModel;
import com.curvelo.database.repository.AvaliationRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AvaliationModelServiceImplTest {

  @Mock
  private AvaliationRepository avaliationRepository;

  @Mock
  private BookService bookService;

  @InjectMocks
  private AvaliationServiceImpl avaliationService;

  @Test
  void shouldCreateAAvaliationWithSuccess() {

    var book = BookModel.builder()
        .id(1)
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

    when(bookService.findOne(1)).thenReturn(book);
    when(avaliationRepository.save(any(AvaliationModel.class)))
        .thenReturn(AvaliationModel.builder()
          .id(2)
          .bookModel(book)
          .comment("Um bom livro")
          .score(3)
          .build());

    var result = avaliationService.create(1,
        AvaliationModel.builder()
          .comment("Um bom livro")
          .score(3)
          .build());

    assertThat(result.getId()).isNotNull();
    assertThat(result.getComment()).isEqualTo("Um bom livro");
    assertThat(result.getScore()).isEqualTo(3);
    assertThat(result.getBookModel().getId()).isEqualTo(1);
    assertThat(result.getBookModel().getAuthor()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getBookModel().getTitle()).isEqualTo("Hobbit");
    assertThat(result.getBookModel().getNumberOfPages()).isEqualTo(250);
    assertThat(result.getBookModel().getIsbn()).isEqualTo("123456789");

    verify(bookService, times(1)).findOne(1);
    verify(avaliationRepository, times(1)).save(any(AvaliationModel.class));
  }

  @Test
  void shouldReturnManyAvaliationWithSuccess() {

    var book = BookModel.builder()
        .id(2)
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

    var user1 = UserModel.builder()
        .id(99)
        .name("Igor")
        .build();

    var avaliation1 = AvaliationModel.builder()
        .id(3)
        .comment("excelente leitura")
        .score(4)
        .bookModel(book)
        .userModel(user1)
        .build();

    var user2 = UserModel.builder()
        .id(98)
        .name("Igor C")
        .build();

    var avaliation2 = AvaliationModel.builder()
        .id(4)
        .comment("boa leitura")
        .score(3)
        .bookModel(book)
        .userModel(user2)
        .build();

    when(avaliationRepository.findByBookId(2))
        .thenReturn(Lists.list(avaliation1, avaliation2));

    var result = avaliationService.findByBook(2);

    assertThat(result).hasSize(2);
    assertThat(result.get(0).getId()).isEqualTo(3);
    assertThat(result.get(0).getComment()).isEqualTo("excelente leitura");
    assertThat(result.get(0).getScore()).isEqualTo(4);
    assertThat(result.get(0).getUserModel().getId()).isEqualTo(99);
    assertThat(result.get(0).getBookModel().getId()).isEqualTo(2);
    assertThat(result.get(0).getBookModel().getAuthor()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.get(0).getBookModel().getTitle()).isEqualTo("Hobbit");
    assertThat(result.get(0).getBookModel().getNumberOfPages()).isEqualTo(250);
    assertThat(result.get(0).getBookModel().getIsbn()).isEqualTo("123456789");

    assertThat(result.get(1).getId()).isEqualTo(4);
    assertThat(result.get(1).getComment()).isEqualTo("boa leitura");
    assertThat(result.get(1).getScore()).isEqualTo(3);
    assertThat(result.get(1).getUserModel().getId()).isEqualTo(98);
    assertThat(result.get(1).getBookModel().getId()).isEqualTo(2);
    assertThat(result.get(1).getBookModel().getAuthor()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.get(1).getBookModel().getTitle()).isEqualTo("Hobbit");
    assertThat(result.get(1).getBookModel().getNumberOfPages()).isEqualTo(250);
    assertThat(result.get(1).getBookModel().getIsbn()).isEqualTo("123456789");

    verify(avaliationRepository, times(1)).findByBookId(2);
  }
}