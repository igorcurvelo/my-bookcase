package com.curvelo.adapter.mysql.gateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.curvelo.core.domain.Isbn;
import com.curvelo.database.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IsbnMysqlRepositoryTest {

  @Mock
  private BookRepository bookRepository;

  @InjectMocks
  private IsbnMysqlRepository isbnMysqlRepository;

  @Test
  void shouldReturnTrueWhenExistsAIsbn() {
    final var isbn = Isbn.of("9788533615540");

    when(bookRepository.existsByIsbn(eq("9788533615540")))
        .thenReturn(Boolean.TRUE);

    boolean result = isbnMysqlRepository.existsByIsbn(isbn);

    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenNotExistsAIsbn() {
    final var isbn = Isbn.of("9788533615540");

    when(bookRepository.existsByIsbn(anyString()))
        .thenReturn(Boolean.FALSE);

    boolean result = isbnMysqlRepository.existsByIsbn(isbn);

    assertThat(result).isFalse();
  }
}