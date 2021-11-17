package com.curvelo.api.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.curvelo.api.dto.BookDTO;
import com.curvelo.core.domain.Author;
import com.curvelo.core.domain.BookDomain;
import com.curvelo.core.domain.Isbn;
import com.curvelo.core.usecase.CreateBookUseCase;
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

  @InjectMocks
  private BookController bookController;

  @Test
  void shouldCreateABookWithSuccess() {
    var bookDto = BookDTO.builder()
        .isbn("978-8532530783")
        .numberOfPages(253)
        .authors(List.of("J.R.R. Tolkien"))
        .title("Hobbit")
        .build();

    final var book = BookDomain.builder()
        .id(12)
        .isbn(Isbn.from("978-8532530783"))
        .numberOfPages(253)
        .authors(List.of(Author.from("J.R.R. Tolkien")))
        .title("Hobbit").build();

    Mockito.when(createBookUseCase.create(Mockito.any(BookDomain.class))).thenReturn(book);

    final var result = bookController.post(bookDto);

    assertThat(result.getId()).isEqualTo(12);
    assertThat(result.getIsbn()).isEqualTo("978-8532530783");
    assertThat(result.getNumberOfPages()).isEqualTo(253);
    assertThat(result.getAuthors()).hasSize(1);
    assertThat(result.getAuthors().get(0)).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getTitle()).isEqualTo("Hobbit");
  }

}