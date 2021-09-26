package com.curvelo.adapter.rest.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.curvelo.core.domain.Author;
import com.curvelo.core.domain.BookDomain;
import com.curvelo.core.domain.Isbn;
import java.util.List;
import org.junit.jupiter.api.Test;

class BookAdapterRestTest {

  @Test
  void shouldMapperBookDomainToBookDto() {
    var book = BookDomain.builder()
        .id(12)
        .isbn(Isbn.from("9788533615540"))
        .numberOfPages(250)
        .authors(List.of(Author.from("J.R.R. Tolkien")))
        .title("Hobbit")
        .build();

    var result = BookAdapterRest.toDTO(book);

    assertThat(result.getId()).isEqualTo(12);
    assertThat(result.getIsbn()).isEqualTo("9788533615540");
    assertThat(result.getNumberOfPages()).isEqualTo(250);
    assertThat(result.getAuthors()).hasSize(1);
    assertThat(result.getAuthors().get(0)).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getTitle()).isEqualTo("Hobbit");
  }

}