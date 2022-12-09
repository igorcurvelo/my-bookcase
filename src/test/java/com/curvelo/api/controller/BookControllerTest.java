package com.curvelo.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.curvelo.adapter.input.restcontroller.api.BookController;
import com.curvelo.adapter.input.restcontroller.dto.BookDto;
import com.curvelo.adapter.input.restcontroller.dto.ReviewDto;
import com.curvelo.adapter.input.restcontroller.dto.TotalReviewsDto;
import com.curvelo.adapter.input.restcontroller.dto.UserDto;
import com.curvelo.adapter.input.restcontroller.dto.UserReviewDto;
import com.curvelo.adapter.rest.mapper.BookAdapterRest;
import com.curvelo.adapter.rest.mapper.TotalReviewsAdapterRest;
import com.curvelo.core.domain.Author;
import com.curvelo.core.domain.Book;
import com.curvelo.core.domain.Isbn;
import com.curvelo.core.domain.TotalReviews;
import com.curvelo.core.domain.User;
import com.curvelo.core.domain.UserReview;
import com.curvelo.core.usecase.CalculateReviewsUseCase;
import com.curvelo.core.usecase.CreateBookUseCase;
import com.curvelo.core.usecase.GetterBookUseCase;
import com.curvelo.database.model.BookModel;
import com.curvelo.database.model.ReviewModel;
import com.curvelo.database.model.UserModel;
import com.curvelo.mapper.ReviewMapper;
import com.curvelo.service.ReviewService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

  @Mock
  private CreateBookUseCase createBookUseCase;

  @Mock
  private GetterBookUseCase getterBookUseCase;

  @Mock
  private BookAdapterRest bookAdapterRest;

  @Mock
  private ReviewService reviewService;

  @Mock
  private CalculateReviewsUseCase calculateReviewsUseCase;

  @Mock
  private TotalReviewsAdapterRest totalReviewsAdapterRest;

  @InjectMocks
  private BookController bookController;

  @Test
  void shouldCreateABookWithSuccess() {
    final var bookDto = BookDto.builder()
        .id(12)
        .isbn("978-8532530783")
        .numberOfPages(253)
        .authors(List.of("J.R.R. Tolkien"))
        .title("Hobbit")
        .build();

    final var book = Book
            .of(12,"Hobbit", Isbn.of("978-8532530783"),
                    List.of(Author.of("J.R.R. Tolkien")), 253, null);

    Mockito.when(bookAdapterRest.toDomain(bookDto)).thenReturn(book);
    Mockito.when(createBookUseCase.create(book)).thenReturn(book);
    Mockito.when(bookAdapterRest.toDto(book)).thenReturn(bookDto);

    final var result = bookController.post(bookDto);

    assertThat(result.getId()).isEqualTo(12);
    assertThat(result.getIsbn()).isEqualTo("978-8532530783");
    assertThat(result.getNumberOfPages()).isEqualTo(253);
    assertThat(result.getAuthors()).hasSize(1);
    assertThat(result.getAuthors().get(0)).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getTitle()).isEqualTo("Hobbit");
  }

  @Test
  void shouldReturnAListWith3Books() {
    final var book = Book
        .of(12,"Hobbit", Isbn.of("978-8532530783"),
            List.of(Author.of("J.R.R. Tolkien")), 253, null);

    final var bookDto = BookDto.builder()
        .id(12)
        .isbn("978-8532530783")
        .numberOfPages(253)
        .authors(List.of("J.R.R. Tolkien"))
        .title("Hobbit")
        .build();

    when(getterBookUseCase.findAll())
        .thenReturn(List.of(book, book, book));

    when(bookAdapterRest.toDto(book)).thenReturn(bookDto);

    final var result = bookController.get();

    assertThat(result).hasSize(3);

    verify(getterBookUseCase, times(1)).findAll();
  }

  @Test
  void shouldCreateAReviewWithSuccess() {
    final var bookId = Integer.valueOf(12);
    final var reviewDto = ReviewDto.builder()
        .book(BookDto.builder()
            .id(bookId)
            .isbn("978-8532530783")
            .numberOfPages(253)
            .authors(List.of("J.R.R. Tolkien"))
            .title("Hobbit")
            .build())
        .score(4)
        .user(UserDto.builder()
            .id(21)
            .name("Name")
            .build())
        .comment("great reading")
        .build();

    final var reviewModelMapping = ReviewMapper.toEntity(reviewDto);
    final var reviewModel = ReviewModel.builder()
        .id(23)
        .book(BookModel.builder()
            .id(bookId)
            .isbn("978-8532530783")
            .numberOfPages(253)
            .author("J.R.R. Tolkien")
            .title("Hobbit")
            .build())
        .score(4)
        .user(UserModel.builder()
            .id(21)
            .name("Name")
            .build())
        .comment("great reading")
        .build();


    when(reviewService.create(eq(bookId), eq(reviewModelMapping)))
        .thenReturn(reviewModel);

    final var result = bookController.postReview(bookId, reviewDto);

    assertThat(result.getId()).isEqualTo(23);
  }

  @Test
  void shouldReturnAListOfReviewsWithSuccess() {
    final var bookId = Integer.valueOf(12);
    final var reviewModel1 = ReviewModel.builder()
        .id(23)
        .book(BookModel.builder()
            .id(bookId)
            .isbn("978-8532530783")
            .numberOfPages(253)
            .author("J.R.R. Tolkien")
            .title("Hobbit")
            .build())
        .score(4)
        .user(UserModel.builder()
            .id(21)
            .name("Name")
            .build())
        .comment("great reading")
        .build();
    final var reviewModel2 = ReviewModel.builder()
        .id(24)
        .book(BookModel.builder()
            .id(bookId)
            .isbn("978-8532530783")
            .numberOfPages(253)
            .author("J.R.R. Tolkien")
            .title("Hobbit")
            .build())
        .score(4)
        .user(UserModel.builder()
            .id(21)
            .name("Name")
            .build())
        .comment("great reading")
        .build();


    when(reviewService.findByBook(eq(bookId)))
        .thenReturn(List.of(reviewModel1,reviewModel2));

    final var result = bookController.getAllReview(bookId);

    assertThat(result).hasSize(2);
    assertThat(result.get(0).getId()).isEqualTo(23);
    assertThat(result.get(1).getId()).isEqualTo(24);
  }

  @Test
  void shouldReturnATotalOfReviewsWithSuccess() {
    final var bookId = Integer.valueOf(12);
    final var totalReviews = TotalReviews.of(
        4.0,
        List.of(UserReview.of("comment1", User.of(1, "Igor"))),
        Book.of(12, "Hobbit",
            Isbn.of("978-8532530783"),
            List.of(Author.of("J.R.R. Tolkien")),
        253, List.of()));

    final var totalDto = TotalReviewsDto.builder()
        .score(4.0)
        .book(BookDto.builder()
            .id(12)
            .isbn("978-8532530783")
            .numberOfPages(253)
            .authors(List.of("J.R.R. Tolkien"))
            .title("Hobbit")
            .build())
        .comments(List.of(
            UserReviewDto.builder()
                .comment("comment1")
                .user(UserDto.builder()
                    .id(23)
                    .name("name")
                    .build())
                .build()
        ))
        .build();


    when(calculateReviewsUseCase.calculateReviewsByBook(eq(bookId)))
        .thenReturn(totalReviews);

    when(totalReviewsAdapterRest.toDto(totalReviews))
        .thenReturn(totalDto);

    final var result = bookController.getAllCalculateReview(bookId);

    assertThat(result.getScore()).isEqualTo(4.0);
    assertThat(result.getBook().getId()).isEqualTo(12);
    assertThat(result.getComments()).hasSize(1);
    assertThat(result.getComments().get(0).getComment()).isEqualTo("comment1");
  }

}