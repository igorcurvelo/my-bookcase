package com.curvelo.adapter.mysql.mapper;

import static com.curvelo.ComposeModel.createBook;
import static com.curvelo.ComposeModel.createReview;
import static com.curvelo.ComposeModel.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.curvelo.core.domain.Author;
import com.curvelo.core.domain.Review;
import com.curvelo.core.domain.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookAdapterMysqlTest {

  @Mock
  private AuthorAdapterMysql authorAdapterMysql;

  @Mock
  private ReviewAdapterMysql reviewAdapterMysql;

  @InjectMocks
  private BookAdapterMysql bookAdapterMysql;

  @Test
  void shouldMapperBookModelToBookDomain() {
    var book = createBook(1).build();
    var user = createUser(2).build();
    var review = createReview(3, user, book).build();

    when(authorAdapterMysql.toDomain(book.getAuthor()))
        .thenReturn(List.of(Author.of("J.R.R. Tolkien")));

    when(reviewAdapterMysql.toDomain(review))
        .thenReturn(Review.of(
            3,
            4,
            "excelente leitura",
            User.of(2, "Igor")
        ));

    var result = bookAdapterMysql.toDomain(book, List.of(review));

    assertThat(result.getId()).isEqualTo(1);
    assertThat(result.getIsbn().getValue()).isEqualTo("9788533615540");
    assertThat(result.getNumberOfPages()).isEqualTo(250);
    assertThat(result.getAuthors()).hasSize(1);
    assertThat(result.getAuthors().get(0).getName()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getTitle()).isEqualTo("Hobbit");

    assertThat(result.getReviews()).hasSize(1);
    assertThat(result.getReviews().get(0).getId()).isEqualTo(3);
    assertThat(result.getReviews().get(0).getComment()).isEqualTo("excelente leitura");
    assertThat(result.getReviews().get(0).getScore()).isEqualTo(4);

    assertThat(result.getReviews().get(0).getUser().getId()).isEqualTo(2);
    assertThat(result.getReviews().get(0).getUser().getName()).isEqualTo("Igor");
  }

}