package com.curvelo.adapter.rest.mapper;

import static com.curvelo.ComposeDomain.createBook;
import static com.curvelo.ComposeDomain.createUser;
import static org.assertj.core.api.Assertions.assertThat;

import com.curvelo.api.dto.BookDTO;
import com.curvelo.api.dto.UserDTO;
import com.curvelo.api.dto.UserReviewDTO;
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
    final var bookDto = new BookDTO(book.getId(),
        book.getTitle(), book.getIsbn().getValue(), List.of("J.R.R. Tolkien"), 250);

    final var user1 = createUser(11);
    final var user2 = User.of(12, "Luiza");

    final var userReviewDomain1 = UserReview.of("Ótimo livro", user1);
    final var userReviewDomain2 = UserReview.of("Boa leitura", user2);

    final var totalReviewsDomain = TotalReviews.of(
        4D, List.of(userReviewDomain1, userReviewDomain2), book);

    Mockito.when(bookAdapterRest.toDTO(book)).thenReturn(bookDto);
    Mockito.when(userReviewAdapterRest.toDTO(userReviewDomain1))
        .thenReturn(new UserReviewDTO(userReviewDomain1.getComment(),
            new UserDTO(11, "Igor")));
    Mockito.when(userReviewAdapterRest.toDTO(userReviewDomain2))
        .thenReturn(new UserReviewDTO(userReviewDomain2.getComment(),
            new UserDTO(12, "Luiza")));

    final var result = totalReviewsAdapterRest.toDTO(totalReviewsDomain);

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