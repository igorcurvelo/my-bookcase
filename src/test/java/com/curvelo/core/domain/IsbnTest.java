package com.curvelo.core.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class IsbnTest {

  @Test
  void shouldCreateAObjectIsbnWithSuccess() {
    var isbn = Isbn.from("978-0-306-40615-7");
    assertThat(isbn).isNotNull();
    assertThat(isbn.getValue()).isEqualTo("978-0-306-40615-7");
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenTheValueHasMoreThan13Characters() {
    assertThatThrownBy(() -> Isbn.from("978-0-306-40615-12"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ISBN must have 13 characters");
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenTheValueHasLetters() {
    assertThatThrownBy(() -> Isbn.from("978-0-306-4a615-7"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ISBN must be a number");
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenTheValueIsInvalid() {
    assertThatThrownBy(() -> Isbn.from("978-0-306-40615-8"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ISBN invalid");
  }

}