package com.curvelo.adapter.rest.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.curvelo.core.domain.Author;
import com.curvelo.core.domain.Book;
import com.curvelo.core.domain.Isbn;
import java.util.List;
import org.junit.jupiter.api.Test;

class BookAdapterRestTest {

  @Test
  void shouldMapperBookDomainToBookDto() {
    final var book = Book.of(
            12, "Hobbit", Isbn.of("9788533615540"),
            List.of(Author.of("J.R.R. Tolkien")),
            250, null);

    final var result = new BookAdapterRest().toDTO(book);

    assertThat(result.getId()).isEqualTo(12);
    assertThat(result.getIsbn()).isEqualTo("9788533615540");
    assertThat(result.getNumberOfPages()).isEqualTo(250);
    assertThat(result.getAuthors()).hasSize(1);
    assertThat(result.getAuthors().get(0)).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getTitle()).isEqualTo("Hobbit");
  }

}