package com.curvelo.adapter.mysql.mapper;

import static com.curvelo.ComposeModel.createBook;
import static com.curvelo.ComposeModel.createReview;
import static com.curvelo.ComposeModel.createUser;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class BookAdapterMysqlTest {

  @Test
  void shouldMapperBookModelToBookDomain() {
    var book = createBook(1).build();
    var user = createUser(2).build();
    var review = createReview(3, user, book).build();

    var result = BookAdapterMysql.toDomain(book, List.of(review));

    assertThat(result.getId()).isEqualTo(1);
    assertThat(result.getIsbn()).isEqualTo("123456789");
    assertThat(result.getNumberOfPages()).isEqualTo(250);
    assertThat(result.getAuthor()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getTitle()).isEqualTo("Hobbit");

    assertThat(result.getReviews()).hasSize(1);
    assertThat(result.getReviews().get(0).getId()).isEqualTo(3);
    assertThat(result.getReviews().get(0).getComment()).isEqualTo("excelente leitura");
    assertThat(result.getReviews().get(0).getScore()).isEqualTo(4);

    assertThat(result.getReviews().get(0).getUser().getId()).isEqualTo(2);
    assertThat(result.getReviews().get(0).getUser().getName()).isEqualTo("Igor");
  }

}