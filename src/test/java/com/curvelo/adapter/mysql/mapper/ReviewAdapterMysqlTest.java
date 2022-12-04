package com.curvelo.adapter.mysql.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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

    when(userAdapterMysql.toDomain(user))
        .thenReturn(User.of(99, "Igor"));

    var result = reviewAdapterMysql.toDomain(review);

    assertThat(result.getId()).isEqualTo(21);
    assertThat(result.getUser().getId()).isEqualTo(99);
    assertThat(result.getComment()).isEqualTo("excelente livro");
    assertThat(result.getScore()).isEqualTo(9);
  }

}