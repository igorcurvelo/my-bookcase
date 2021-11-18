package com.curvelo.core.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.curvelo.ComposeDomain.*;
import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

  @Test
  void shouldCalculateAverageScore() {
    var user1 = createUser(99).build();
    var review1 = createReview(1, user1).build();

    var user2 = createUser(98).name("Curvelo").build();
    var review2 = createReview(2, user2)
        .comment("boa leitura")
        .score(3)
        .build();

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
    var user1 = createUser(99).build();
    var review1 = createReview(1, user1).build();

    var user2 = createUser(98).name("Curvelo").build();
    var review2 = createReview(2, user2)
        .comment("boa leitura")
        .score(3)
        .build();

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

}