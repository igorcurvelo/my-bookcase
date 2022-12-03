package com.curvelo.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.curvelo.adapter.rest.mapper.BookAdapterRest;
import com.curvelo.adapter.input.restcontroller.api.BookController;
import com.curvelo.adapter.input.restcontroller.dto.BookDTO;
import com.curvelo.core.domain.Author;
import com.curvelo.core.domain.Book;
import com.curvelo.core.domain.Isbn;
import com.curvelo.core.usecase.CreateBookUseCase;
import com.curvelo.core.usecase.GetterBookUseCase;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

  @Mock
  private CreateBookUseCase createBookUseCase;

  @Mock
  private GetterBookUseCase getterBookUseCase;

  @Mock
  private BookAdapterRest bookAdapterRest;

  @InjectMocks
  private BookController bookController;

  @Test
  void shouldCreateABookWithSuccess() {
    final var bookDto = BookDTO.builder()
        .id(12)
        .isbn("978-8532530783")
        .numberOfPages(253)
        .authors(List.of("J.R.R. Tolkien"))
        .title("Hobbit")
        .build();

    final var book = Book
            .of(12,"Hobbit", Isbn.of("978-8532530783"),
                    List.of(Author.of("J.R.R. Tolkien")), 253, null);

    Mockito.when(bookAdapterRest.toDomain(bookDto)).thenReturn(book);
    Mockito.when(createBookUseCase.create(book)).thenReturn(book);
    Mockito.when(bookAdapterRest.toDTO(book)).thenReturn(bookDto);

    final var result = bookController.post(bookDto);

    assertThat(result.getId()).isEqualTo(12);
    assertThat(result.getIsbn()).isEqualTo("978-8532530783");
    assertThat(result.getNumberOfPages()).isEqualTo(253);
    assertThat(result.getAuthors()).hasSize(1);
    assertThat(result.getAuthors().get(0)).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getTitle()).isEqualTo("Hobbit");
  }

  @Test
  void shouldReturnAListWith3Books() {
    final var book = Book
        .of(12,"Hobbit", Isbn.of("978-8532530783"),
            List.of(Author.of("J.R.R. Tolkien")), 253, null);

    final var bookDto = BookDTO.builder()
        .id(12)
        .isbn("978-8532530783")
        .numberOfPages(253)
        .authors(List.of("J.R.R. Tolkien"))
        .title("Hobbit")
        .build();

    when(getterBookUseCase.findAll())
        .thenReturn(List.of(book, book, book));

    when(bookAdapterRest.toDTO(book)).thenReturn(bookDto);

    final var result = bookController.get();

    assertThat(result).hasSize(3);

    verify(getterBookUseCase, times(1)).findAll();
  }

}