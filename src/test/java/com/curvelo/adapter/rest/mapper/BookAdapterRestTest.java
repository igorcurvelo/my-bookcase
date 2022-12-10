package com.curvelo.adapter.rest.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.curvelo.adapter.input.restcontroller.dto.BookDto;
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

    final var result = new BookAdapterRest().toDto(book);

    assertThat(result.id()).isEqualTo(12);
    assertThat(result.isbn()).isEqualTo("9788533615540");
    assertThat(result.numberOfPages()).isEqualTo(250);
    assertThat(result.authors()).hasSize(1);
    assertThat(result.authors().get(0)).isEqualTo("J.R.R. Tolkien");
    assertThat(result.title()).isEqualTo("Hobbit");
  }

  @Test
  void shouldMapperBookDtoToBookDomain() {
    final var bookDto = BookDto.builder()
        .id(12)
        .isbn("9788533615540")
        .numberOfPages(253)
        .authors(List.of("J.R.R. Tolkien"))
        .title("Hobbit")
        .build();

    final var result = new BookAdapterRest().toDomain(bookDto);

    assertThat(result.getId()).isEqualTo(12);
    assertThat(result.getIsbn().getValue()).isEqualTo("9788533615540");
    assertThat(result.getNumberOfPages()).isEqualTo(253);
    assertThat(result.getAuthors()).hasSize(1);
    assertThat(result.getAuthors().get(0).getName()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getTitle()).isEqualTo("Hobbit");
  }
}