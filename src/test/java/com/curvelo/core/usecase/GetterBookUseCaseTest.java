package com.curvelo.core.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.curvelo.core.domain.Author;
import com.curvelo.core.domain.Book;
import com.curvelo.core.domain.Isbn;
import com.curvelo.core.repository.BookDomainRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetterBookUseCaseTest {

  @InjectMocks
  private GetterBookUseCase getterBookUseCase;

  @Mock
  private BookDomainRepository bookDomainRepository;

  @Test
  void shouldReturnAllBooks() {
    final var book1 = Book.of(
        12, "Hobbit", Isbn.of("9788533615540"),
        List.of(Author.of("J.R.R. Tolkien")),
        250, null);
    final var book2 = Book.of(
        12, "Hobbit", Isbn.of("9788533615540"),
        List.of(Author.of("J.R.R. Tolkien")),
        250, null);

    when(bookDomainRepository.findAll())
        .thenReturn(List.of(book1, book2));

    final var result = getterBookUseCase.findAll();

    assertThat(result).hasSize(2);
  }
}