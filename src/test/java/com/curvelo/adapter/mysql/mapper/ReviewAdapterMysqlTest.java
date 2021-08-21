package com.curvelo.adapter.mysql.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.curvelo.database.model.BookModel;
import com.curvelo.database.model.ReviewModel;
import com.curvelo.database.model.UserModel;
import org.junit.jupiter.api.Test;

class ReviewAdapterMysqlTest {

  @Test
  void shouldMapperReviewModelToReviewDomain() {
    var book = BookModel.builder()
        .id(12)
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

    var user = UserModel.builder()
        .id(99)
        .name("Igor")
        .build();

    var review = ReviewModel.builder()
        .id(21)
        .book(book)
        .user(user)
        .comment("excelente livro")
        .score(9)
        .build();

    var result = ReviewAdapterMysql.toDomain(review);

    assertThat(result.getId()).isEqualTo(21);
    assertThat(result.getBook().getId()).isEqualTo(12);
    assertThat(result.getUser().getId()).isEqualTo(99);
    assertThat(result.getComment()).isEqualTo("excelente livro");
    assertThat(result.getScore()).isEqualTo(9);
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenModelIsNull() {
    assertThatThrownBy(() -> ReviewAdapterMysql.toDomain(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

}