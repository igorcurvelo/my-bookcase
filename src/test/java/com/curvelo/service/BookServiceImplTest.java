package com.curvelo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.curvelo.domain.model.Book;
import com.curvelo.repository.BookRepository;
import java.util.List;
import javax.persistence.EntityExistsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
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

    assertEquals(3, result.size());

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

    assertNotNull(result.getId());
    assertEquals("123456789", result.getIsbn());
    assertEquals(Integer.valueOf(250), result.getNumberOfPages());
    assertEquals("J.R.R. Tolkien", result.getAuthor());
    assertEquals("Hobbit", result.getTitle());

    verify(bookRepository, times(1)).save(book);

  }

  @Test(expected = EntityExistsException.class)
  public void shouldReturnEntityExistsExceptionWhenExistABookWithTheSameIsbn() {

    var book = Book.builder()
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

    when(bookRepository.existsByIsbn("123456789"))
        .thenReturn(true);

    bookService.create(book);

    verify(bookRepository, times(0)).save(any(Book.class));
  }

}
