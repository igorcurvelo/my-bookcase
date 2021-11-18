package com.curvelo.core.usecase;

import com.curvelo.core.repository.BookDomainRepository;
import com.curvelo.core.repository.IsbnDomainRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;

import static com.curvelo.ComposeDomain.createBook;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateBookUseCaseTest {

  @Mock
  private BookDomainRepository bookDomainRepository;

  @Mock
  private IsbnDomainRepository isbnDomainRepository;

  @InjectMocks
  private CreateBookUseCase createBookUseCase;

  @Test
  void shouldCreateABook() {
    var book = createBook(11);

    when(isbnDomainRepository.existsByIsbn(book.getIsbn())).thenReturn(false);
    when(bookDomainRepository.save(book)).thenReturn(book);

    var result = createBookUseCase.create(book);

    assertThat(result.getId()).isEqualTo(book.getId());
    assertThat(result.getTitle()).isEqualTo(book.getTitle());
    assertThat(result.getIsbn()).isEqualTo(book.getIsbn());
    assertThat(result.getAuthors()).hasSize(1);
    assertThat(result.getAuthors().get(0).getName()).isEqualTo(book.getAuthors().get(0).getName());
    assertThat(result.getNumberOfPages()).isEqualTo(book.getNumberOfPages());
    assertThat(result.getAverageScore()).isZero();
    assertThat(result.getComments()).isEmpty();
    assertThat(result.getReviews()).isEmpty();
  }

  @Test
  void shouldReturnErrorEntityExistExceptionWhenIsbnAlreadyExists() {
    var book = createBook(11);

    when(isbnDomainRepository.existsByIsbn(book.getIsbn())).thenReturn(true);

    assertThatThrownBy(() -> createBookUseCase.create(book))
        .isInstanceOf(EntityExistsException.class)
        .hasMessage("book already registered");
  }
}