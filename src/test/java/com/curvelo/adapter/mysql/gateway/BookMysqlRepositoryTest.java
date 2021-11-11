package com.curvelo.adapter.mysql.gateway;

import static com.curvelo.ComposeModel.createBook;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.curvelo.database.repository.BookRepository;
import com.curvelo.database.repository.ReviewRepository;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookMysqlRepositoryTest {

  @Mock
  private BookRepository bookRepository;

  @Mock
  private ReviewRepository reviewRepository;

  @InjectMocks
  private BookMysqlRepository bookMysqlRepository;

  @Test
  void shouldReturnABookById() {

    var bookModel = createBook(12).build();

    when(bookRepository.findById(12))
        .thenReturn(Optional.of(bookModel));

    var result = bookMysqlRepository.findById(12);

    assertThat(result.getId()).isEqualTo(12);
    assertThat(result.getIsbn().getValue()).isEqualTo("9788533615540");
    assertThat(result.getNumberOfPages()).isEqualTo(250);
    assertThat(result.getAuthors()).hasSize(1);
    assertThat(result.getAuthors().get(0).getName()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getTitle()).isEqualTo("Hobbit");

    verify(bookRepository, times(1)).findById(12);
  }

  @Test
  void shouldReturnExceptionWhenBookDoesNotExist() {
    when(bookRepository.findById(123))
        .thenThrow(new EntityNotFoundException("Book not found"));

    assertThatThrownBy(() -> bookMysqlRepository.findById(123))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("Book not found");
  }

}