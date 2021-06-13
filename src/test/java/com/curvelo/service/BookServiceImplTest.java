package com.curvelo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.curvelo.domain.model.Book;
import com.curvelo.repository.BookRepository;
import java.util.List;
import javax.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

  @Mock
  private BookRepository bookRepository;

  @InjectMocks
  private BookServiceImpl bookService;

  @Test
  public void shouldReturnAListWith3Books() {

    var book = Book.builder().build();

    when(bookRepository.findAll())
        .thenReturn(List.of(book, book, book));

    var result = bookService.findAll();

    assertThat(result).hasSize(3);

    verify(bookRepository, times(1)).findAll();
  }

  @Test
  public void shouldCreateABookWithSuccess() {

    var book = Book.builder()
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

    when(bookRepository.save(book))
        .thenReturn(Book.builder()
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
  public void shouldReturnEntityExistsExceptionWhenExistABookWithTheSameIsbn() {

    var book = Book.builder()
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

    verify(bookRepository, times(0)).save(any(Book.class));
  }

}
