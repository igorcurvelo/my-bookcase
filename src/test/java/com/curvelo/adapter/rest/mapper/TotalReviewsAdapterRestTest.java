package com.curvelo.adapter.rest.mapper;

import static com.curvelo.ComposeDomain.createBook;
import static com.curvelo.ComposeDomain.createUser;
import static org.assertj.core.api.Assertions.assertThat;

import com.curvelo.core.domain.TotalReviews;
import com.curvelo.core.domain.User;
import com.curvelo.core.domain.UserReview;
import java.util.List;
import org.junit.jupiter.api.Test;

class TotalReviewsAdapterRestTest {

  @Test
  void shouldMapperTotalReviewDomainToTotalReviewDto() {
    var book = createBook(22);

    var user1 = createUser(11);
    var user2 = User.of(12, "Luiza");

    var userReviewDomain1 = UserReview.of("Ótimo livro", user1);
    var userReviewDomain2 = UserReview.of("Boa leitura", user2);

    var totalReviewsDomain = TotalReviews.of(
        4D, List.of(userReviewDomain1, userReviewDomain2), book);

    var result = TotalReviewsAdapterRest.toDTO(totalReviewsDomain);

    assertThat(result.getBook().getId()).isEqualTo(22);
    assertThat(result.getBook().getTitle()).isEqualTo("Hobbit");
    assertThat(result.getBook().getAuthors()).hasSize(1);
    assertThat(result.getBook().getAuthors().get(0)).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getBook().getIsbn()).isEqualTo("9788533615540");
    assertThat(result.getBook().getNumberOfPages()).isEqualTo(250);

    assertThat(result.getComments()).hasSize(2);
    assertThat(result.getComments().get(0).getUser().getId()).isEqualTo(11);
    assertThat(result.getComments().get(0).getUser().getName()).isEqualTo("Igor");
    assertThat(result.getComments().get(0).getComment()).isEqualTo("Ótimo livro");

    assertThat(result.getComments().get(1).getUser().getId()).isEqualTo(12);
    assertThat(result.getComments().get(1).getUser().getName()).isEqualTo("Luiza");
    assertThat(result.getComments().get(1).getComment()).isEqualTo("Boa leitura");

    assertThat(result.getScore()).isEqualTo(4D);

  }

}