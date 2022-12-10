package com.curvelo.adapter.rest.mapper;

import static com.curvelo.ComposeDomain.createBook;
import static com.curvelo.ComposeDomain.createUser;
import static org.assertj.core.api.Assertions.assertThat;

import com.curvelo.adapter.input.restcontroller.dto.BookDto;
import com.curvelo.adapter.input.restcontroller.dto.UserDto;
import com.curvelo.adapter.input.restcontroller.dto.UserReviewDto;
import com.curvelo.core.domain.TotalReviews;
import com.curvelo.core.domain.User;
import com.curvelo.core.domain.UserReview;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TotalReviewsAdapterRestTest {

  @Mock
  private BookAdapterRest bookAdapterRest;

  @Mock
  private UserReviewAdapterRest userReviewAdapterRest;

  @InjectMocks
  private TotalReviewsAdapterRest totalReviewsAdapterRest;

  @Test
  void shouldMapperTotalReviewDomainToTotalReviewDto() {
    final var book = createBook(22);
    final var bookDto = new BookDto(book.getId(),
        book.getTitle(), book.getIsbn().getValue(), List.of("J.R.R. Tolkien"), 250);

    final var user1 = createUser(11);
    final var user2 = User.of(12, "Luiza");

    final var userReviewDomain1 = UserReview.of("Ótimo livro", user1);
    final var userReviewDomain2 = UserReview.of("Boa leitura", user2);

    final var totalReviewsDomain = TotalReviews.of(
        4D, List.of(userReviewDomain1, userReviewDomain2), book);

    Mockito.when(bookAdapterRest.toDto(book)).thenReturn(bookDto);
    Mockito.when(userReviewAdapterRest.toDto(userReviewDomain1))
        .thenReturn(new UserReviewDto(userReviewDomain1.getComment(),
            new UserDto(11, "Igor")));
    Mockito.when(userReviewAdapterRest.toDto(userReviewDomain2))
        .thenReturn(new UserReviewDto(userReviewDomain2.getComment(),
            new UserDto(12, "Luiza")));

    final var result = totalReviewsAdapterRest.toDto(totalReviewsDomain);

    assertThat(result.book().id()).isEqualTo(22);
    assertThat(result.book().title()).isEqualTo("Hobbit");
    assertThat(result.book().authors()).hasSize(1);
    assertThat(result.book().authors().get(0)).isEqualTo("J.R.R. Tolkien");
    assertThat(result.book().isbn()).isEqualTo("9788533615540");
    assertThat(result.book().numberOfPages()).isEqualTo(250);

    assertThat(result.comments()).hasSize(2);
    assertThat(result.comments().get(0).user().id()).isEqualTo(11);
    assertThat(result.comments().get(0).user().name()).isEqualTo("Igor");
    assertThat(result.comments().get(0).comment()).isEqualTo("Ótimo livro");

    assertThat(result.comments().get(1).user().id()).isEqualTo(12);
    assertThat(result.comments().get(1).user().name()).isEqualTo("Luiza");
    assertThat(result.comments().get(1).comment()).isEqualTo("Boa leitura");

    assertThat(result.score()).isEqualTo(4D);

  }

}