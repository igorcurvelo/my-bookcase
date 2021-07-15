package com.curvelo.core;

import static com.curvelo.Compose.createAvaliation;
import static com.curvelo.Compose.createBook;
import static com.curvelo.Compose.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.curvelo.repository.AvaliationRepository;
import com.curvelo.repository.BookRepository;
import java.util.Collections;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TotalizeAvaliationTest {

  @Mock
  private AvaliationRepository avaliationRepository;

  @Mock
  private BookRepository bookRepository;

  @InjectMocks
  private TotalizeAvaliation totalizeAvaliation;

  @Test
  public void shouldTotalizeTwoAvaliationByBook() {
    var book = createBook(11).build();
    var user1 = createUser(99).build();
    var avaliation1 = createAvaliation(1, user1, book).build();

    var user2 = createUser(98).name("Curvelo").build();
    var avaliation2 = createAvaliation(2, user2, book)
        .comment("boa leitura")
        .score(3)
        .build();

    when(avaliationRepository.findByBookId(book.getId()))
        .thenReturn(Lists.list(avaliation1, avaliation2));

    when(bookRepository.findById(book.getId()))
        .thenReturn(Optional.of(book));

    var result = totalizeAvaliation.totalizeAvaliationsByBook(book.getId());

    assertThat(result.getScore()).isEqualTo(3.5);
    assertThat(result.getBook().getId()).isEqualTo(book.getId());
    assertThat(result.getBook().getTitle()).isEqualTo(book.getTitle());
    assertThat(result.getComments()).hasSize(2);
    assertThat(result.getComments().get(0).getUser().getId()).isEqualTo(user1.getId());
    assertThat(result.getComments().get(0).getUser().getName()).isEqualTo(user1.getName());
    assertThat(result.getComments().get(0).getComment()).isEqualTo(avaliation1.getComment());

    assertThat(result.getComments().get(1).getUser().getId()).isEqualTo(user2.getId());
    assertThat(result.getComments().get(1).getUser().getName()).isEqualTo(user2.getName());
    assertThat(result.getComments().get(1).getComment()).isEqualTo(avaliation2.getComment());
  }

  @Test
  public void shouldReturnDefaultTotalizeAvaliationByBookWhenBookDoesntExist() {
    when(bookRepository.findById(123))
        .thenReturn(Optional.empty());

    assertThatThrownBy(() -> totalizeAvaliation.totalizeAvaliationsByBook(123))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("Book not found");
  }

  @Test
  public void shouldReturnDefaultTotalizeAvaliationByBookWhenAvaliationDoesntExist() {
    var book = createBook(11).build();

    when(bookRepository.findById(book.getId()))
        .thenReturn(Optional.of(book));

    when(avaliationRepository.findByBookId(book.getId()))
        .thenReturn(Collections.emptyList());

    var result = totalizeAvaliation.totalizeAvaliationsByBook(book.getId());

    assertThat(result.getScore()).isEqualTo(0.0);
    assertThat(result.getBook().getId()).isEqualTo(book.getId());
    assertThat(result.getBook().getTitle()).isEqualTo(book.getTitle());
    assertThat(result.getComments()).hasSize(0);
  }

}