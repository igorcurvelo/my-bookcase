package com.curvelo.adapter.mysql.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.curvelo.core.domain.Review;
import com.curvelo.core.domain.User;
import com.curvelo.database.model.BookModel;
import com.curvelo.database.model.ReviewModel;
import com.curvelo.database.model.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewAdapterMysqlTest {

  @Mock
  private UserAdapterMysql userAdapterMysql;

  @InjectMocks
  private ReviewAdapterMysql reviewAdapterMysql;

  @Test
  void shouldMapperReviewModelToReviewDomain() {
    final var book = BookModel.builder()
        .id(12)
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

    final var user = UserModel.builder()
        .id(99)
        .name("Igor")
        .build();

    final var review = ReviewModel.builder()
        .id(21)
        .book(book)
        .user(user)
        .comment("excelente livro")
        .score(9)
        .build();

    when(userAdapterMysql.toDomain(user))
        .thenReturn(User.of(99, "Igor"));

    final var result = reviewAdapterMysql.toDomain(review);

    assertThat(result.getId()).isEqualTo(21);
    assertThat(result.getUser().getId()).isEqualTo(99);
    assertThat(result.getComment()).isEqualTo("excelente livro");
    assertThat(result.getScore()).isEqualTo(9);
  }

  @Test
  void shouldMapperReviewDomainToReviewModel() {
    final var user = User.of(21, "name");

    final var review = Review.of(
        121,
        4,
        "excelente leitura",
        user
    );

    when(userAdapterMysql.toModel(user))
        .thenReturn(UserModel.builder().id(21).name("name").build());

    final var result = reviewAdapterMysql.toModel(review);

    assertThat(result.getId()).isEqualTo(121);
    assertThat(result.getUser().getId()).isEqualTo(21);
    assertThat(result.getComment()).isEqualTo("excelente leitura");
    assertThat(result.getScore()).isEqualTo(4);
  }

}