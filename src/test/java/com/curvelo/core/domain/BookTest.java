package com.curvelo.core.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.curvelo.ComposeDomain.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookTest {

  @Test
  void shouldCalculateAverageScore() {
    var user1 = createUser(99);
    var review1 = createReview(1, user1);

    var user2 = User.of(98, "Curvelo");
    var review2 = Review.of(
        2, 3, "boa leitura", user2);

    var book = createBook(11, Arrays.asList(review1, review2));

    assertThat(book.getAverageScore()).isEqualTo(3.5);
  }

  @Test
  void shouldCalculateAverageScoreWhenBookWithoutReviews() {
    var book = createBook(12);

    assertThat(book.getAverageScore()).isZero();
  }

  @Test
  void shouldReturnAllComments() {
    var user1 = createUser(99);
    var review1 = createReview(1, user1);

    var user2 = User.of(98, "Curvelo");
    var review2 = Review.of(
        2, 3, "boa leitura", user2);

    var book = createBook(11, Arrays.asList(review1, review2));

    assertThat(book.getComments()).hasSize(2);
    assertThat(book.getComments().get(0).getUser().getId()).isEqualTo(user1.getId());
    assertThat(book.getComments().get(0).getUser().getName()).isEqualTo(user1.getName());
    assertThat(book.getComments().get(0).getComment()).isEqualTo(review1.getComment());

    assertThat(book.getComments().get(1).getUser().getId()).isEqualTo(user2.getId());
    assertThat(book.getComments().get(1).getUser().getName()).isEqualTo(user2.getName());
    assertThat(book.getComments().get(1).getComment()).isEqualTo(review2.getComment());
  }

  @Test
  void shouldReturnAEmptyListOfCommentsWhenBookWithoutReviews() {
    var book = createBook(12);

    assertThat(book.getComments()).isEmpty();
  }

  @Test
  void shouldReturnNullExceptionWhenAListOfAuthorsIsEmpty() {
    assertThatThrownBy(() ->
      Book.of(
              1,
              "title",
              Isbn.of("9788533615540"),
              List.of(),
              123,
              null
      )).isInstanceOf(NullPointerException.class);
  }

}