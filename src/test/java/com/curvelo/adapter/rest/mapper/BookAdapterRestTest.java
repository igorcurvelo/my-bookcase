package com.curvelo.adapter.rest.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.curvelo.core.domain.BookDomain;
import org.junit.jupiter.api.Test;

class BookAdapterRestTest {

  @Test
  void shouldMapperBookDomainToBookDto() {
    var book = BookDomain.builder()
        .id(12)
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

    var result = BookAdapterRest.toDTO(book);

    assertThat(result.getId()).isEqualTo(12);
    assertThat(result.getIsbn()).isEqualTo("123456789");
    assertThat(result.getNumberOfPages()).isEqualTo(250);
    assertThat(result.getAuthor()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getTitle()).isEqualTo("Hobbit");
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenDomainIsNull() {
    assertThatThrownBy(() -> BookAdapterRest.toDTO(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

}