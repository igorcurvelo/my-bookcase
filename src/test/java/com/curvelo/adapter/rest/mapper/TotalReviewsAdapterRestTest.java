package com.curvelo.adapter.rest.mapper;

import static com.curvelo.ComposeDomain.createBook;
import static com.curvelo.ComposeDomain.createUser;
import static org.assertj.core.api.Assertions.assertThat;

import com.curvelo.core.domain.TotalReviewsDomain;
import com.curvelo.core.domain.UserReviewDomain;
import java.util.List;
import org.junit.jupiter.api.Test;

class TotalReviewsAdapterRestTest {

  @Test
  void shouldMapperTotalReviewDomainToTotalReviewDto() {
    var book = createBook(22).build();

    var user1 = createUser(11).build();
    var user2 = createUser(12).name("Luiza").build();

    var userReviewDomain1 = UserReviewDomain.builder()
        .user(user1).comment("Ótimo livro").build();

    var userReviewDomain2 = UserReviewDomain.builder()
        .user(user2).comment("Boa leitura").build();

    var totalReviewsDomain = TotalReviewsDomain.builder()
        .book(book)
        .comments(List.of(userReviewDomain1, userReviewDomain2))
        .score(4D)
        .build();

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