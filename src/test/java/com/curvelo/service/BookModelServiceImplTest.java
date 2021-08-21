package com.curvelo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.curvelo.database.model.BookModel;
import com.curvelo.database.repository.BookRepository;
import java.util.List;
import javax.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookModelServiceImplTest {

  @Mock
  private BookRepository bookRepository;

  @InjectMocks
  private BookServiceImpl bookService;

  @Test
  void shouldReturnAListWith3Books() {

    var book = BookModel.builder().build();

    when(bookRepository.findAll())
        .thenReturn(List.of(book, book, book));

    var result = bookService.findAll();

    assertThat(result).hasSize(3);

    verify(bookRepository, times(1)).findAll();
  }

  @Test
  void shouldCreateABookWithSuccess() {

    var book = BookModel.builder()
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

    when(bookRepository.save(book))
        .thenReturn(BookModel.builder()
            .id(1)
            .isbn("123456789")
            .numberOfPages(250)
            .author("J.R.R. Tolkien")
            .title("Hobbit")
            .build());

    var result = bookService.create(book);

    assertThat(result.getId()).isNotNull();
    assertThat(result.getIsbn()).isEqualTo("123456789");
    assertThat(result.getNumberOfPages()).isEqualTo(250);
    assertThat(result.getAuthor()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getTitle()).isEqualTo("Hobbit");

    verify(bookRepository, times(1)).save(book);

  }

  @Test
  void shouldReturnEntityExistsExceptionWhenExistABookWithTheSameIsbn() {

    var book = BookModel.builder()
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

    when(bookRepository.existsByIsbn("123456789"))
        .thenReturn(true);

    assertThatThrownBy(() -> bookService.create(book))
        .isInstanceOf(EntityExistsException.class)
        .hasMessageContaining("book already registered");

    verify(bookRepository, times(0)).save(any(BookModel.class));
  }

}
