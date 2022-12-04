package com.curvelo.adapter.mysql.gateway;

import static com.curvelo.ComposeModel.createBook;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.curvelo.adapter.mysql.mapper.BookAdapterMysql;
import com.curvelo.core.domain.Author;
import com.curvelo.core.domain.Book;
import com.curvelo.core.domain.Isbn;
import com.curvelo.core.domain.Review;
import com.curvelo.core.domain.User;
import com.curvelo.database.model.BookModel;
import com.curvelo.database.model.ReviewModel;
import com.curvelo.database.model.UserModel;
import com.curvelo.database.repository.BookRepository;
import com.curvelo.database.repository.ReviewRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookMysqlRepositoryTest {

  @Mock
  private BookRepository bookRepository;

  @Mock
  private ReviewRepository reviewRepository;

  @Mock
  private BookAdapterMysql bookAdapterMysql;

  @InjectMocks
  private BookMysqlRepository bookMysqlRepository;

  @Test
  void shouldReturnABookById() {

    final var bookModel = createBook(12).build();

    final var reviewsModel = List.of(ReviewModel.builder()
        .id(123)
        .score(4)
        .comment("great book")
        .book(bookModel)
        .user(UserModel.builder().id(321).name("Name").build())
        .build());

    final var book = Book.of(
        12,
        "Hobbit",
        Isbn.of("9788533615540"),
        List.of(Author.of("J.R.R. Tolkien")),
        250,
        List.of(Review.of(
            123,
            4,
            "great book",
            User.of(321, "Name")
        ))
    );

    when(bookRepository.findById(12))
        .thenReturn(Optional.of(bookModel));

    when(reviewRepository.findByBookId(12))
        .thenReturn(reviewsModel);

    when(bookAdapterMysql.toDomain(bookModel, reviewsModel))
        .thenReturn(book);

    final var result = bookMysqlRepository.findById(12);

    assertThat(result.getId()).isEqualTo(12);
    assertThat(result.getIsbn().getValue()).isEqualTo("9788533615540");
    assertThat(result.getNumberOfPages()).isEqualTo(250);
    assertThat(result.getAuthors()).hasSize(1);
    assertThat(result.getAuthors().get(0).getName()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getTitle()).isEqualTo("Hobbit");

    verify(bookRepository, times(1)).findById(12);
  }

  @Test
  void shouldReturnExceptionWhenBookDoesNotExist() {
    when(bookRepository.findById(123))
        .thenThrow(new EntityNotFoundException("Book not found"));

    assertThatThrownBy(() -> bookMysqlRepository.findById(123))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("Book not found");
  }

  @Test
  void shouldReturnExceptionWhenBook() {
    when(bookRepository.findById(123))
        .thenReturn(Optional.empty());

    assertThatThrownBy(() -> bookMysqlRepository.findById(123))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("Book not found");
  }

  @Test
  void shouldSaveANewBook() {
    final var bookModel = BookModel.builder()
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .isbn("978-8532530783")
        .numberOfPages(253)
        .build();
    final var bookModelSaved = BookModel.builder()
        .id(24)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .isbn("978-8532530783")
        .numberOfPages(253)
        .build();
    final var book =  Book.of(null, "Hobbit",
        Isbn.of("978-8532530783"),
        List.of(Author.of("J.R.R. Tolkien")),
        253, List.of());

    final var bookResult =  Book.of(24, "Hobbit",
        Isbn.of("978-8532530783"),
        List.of(Author.of("J.R.R. Tolkien")),
        253, List.of());

    when(bookAdapterMysql.toModel(book))
        .thenReturn(bookModel);

    when(bookRepository.save(bookModel))
        .thenReturn(bookModelSaved);

    when(bookAdapterMysql.toDomain(bookModelSaved))
        .thenReturn(bookResult);

    final var result = bookMysqlRepository.save(book);
    assertThat(result.getId()).isEqualTo(24);
    assertThat(result.getIsbn().getValue()).isEqualTo("978-8532530783");
    assertThat(result.getNumberOfPages()).isEqualTo(253);
    assertThat(result.getAuthors()).hasSize(1);
    assertThat(result.getAuthors().get(0).getName()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getTitle()).isEqualTo("Hobbit");
  }

  @Test
  void shouldReturnAListOfBook() {

    var bookModel = createBook(12).build();

    final var bookResult =  Book.of(12, "Hobbit",
        Isbn.of("9788533615540"),
        List.of(Author.of("J.R.R. Tolkien")),
        250, List.of());

    when(bookRepository.findAll())
        .thenReturn(List.of(bookModel));

    when(bookAdapterMysql.toDomain(bookModel))
        .thenReturn(bookResult);

    var result = bookMysqlRepository.findAll();

    assertThat(result).hasSize(1);
    assertThat(result.get(0).getId()).isEqualTo(12);
    assertThat(result.get(0).getIsbn().getValue()).isEqualTo("9788533615540");
    assertThat(result.get(0).getNumberOfPages()).isEqualTo(250);
    assertThat(result.get(0).getAuthors()).hasSize(1);
    assertThat(result.get(0).getAuthors().get(0).getName()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.get(0).getTitle()).isEqualTo("Hobbit");

    verify(bookRepository, times(1)).findAll();
  }

}