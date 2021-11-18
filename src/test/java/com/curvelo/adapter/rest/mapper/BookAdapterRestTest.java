package com.curvelo.adapter.rest.mapper;

import com.curvelo.core.domain.Author;
import com.curvelo.core.domain.Book;
import com.curvelo.core.domain.Isbn;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookAdapterRestTest {

  @Test
  void shouldMapperBookDomainToBookDto() {
    var book = Book.of(
            12, "Hobbit", Isbn.from("9788533615540"), List.of(Author.of("J.R.R. Tolkien")),
            250, null);

    var result = BookAdapterRest.toDTO(book);

    assertThat(result.getId()).isEqualTo(12);
    assertThat(result.getIsbn()).isEqualTo("9788533615540");
    assertThat(result.getNumberOfPages()).isEqualTo(250);
    assertThat(result.getAuthors()).hasSize(1);
    assertThat(result.getAuthors().get(0)).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getTitle()).isEqualTo("Hobbit");
  }

}