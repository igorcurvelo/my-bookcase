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
import com.curvelo.adapter.rest.mapper.ReviewAdapterRest;
import com.curvelo.adapter.rest.mapper.TotalReviewsAdapterRest;
import com.curvelo.core.domain.Author;
import com.curvelo.core.domain.Book;
import com.curvelo.core.domain.Isbn;
import com.curvelo.core.domain.Review;
import com.curvelo.core.domain.TotalReviews;
import com.curvelo.core.domain.User;
import com.curvelo.core.domain.UserReview;
import com.curvelo.core.usecase.CalculateReviewsUseCase;
import com.curvelo.core.usecase.CreateBookUseCase;
import com.curvelo.core.usecase.CreatorReviewUseCase;
import com.curvelo.core.usecase.GetterBookUseCase;
import com.curvelo.core.usecase.GetterReviewUseCase;
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
  private CreatorReviewUseCase creatorReviewUseCase;

  @Mock
  private GetterBookUseCase getterBookUseCase;

  @Mock
  private GetterReviewUseCase getterReviewUseCase;

  @Mock
  private CalculateReviewsUseCase calculateReviewsUseCase;

  @Mock
  private BookAdapterRest bookAdapterRest;

  @Mock
  private ReviewAdapterRest reviewAdapterRest;

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

    assertThat(result.id()).isEqualTo(12);
    assertThat(result.isbn()).isEqualTo("978-8532530783");
    assertThat(result.numberOfPages()).isEqualTo(253);
    assertThat(result.authors()).hasSize(1);
    assertThat(result.authors().get(0)).isEqualTo("J.R.R. Tolkien");
    assertThat(result.title()).isEqualTo("Hobbit");
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
        .score(4)
        .user(UserDto.builder()
            .id(21)
            .name("Name")
            .build())
        .comment("great reading")
        .build();

    final var reviewDtoResponse = ReviewDto.builder()
        .id(23)
        .score(4)
        .user(UserDto.builder()
            .id(21)
            .name("Name")
            .build())
        .comment("great reading")
        .build();

    final var reviewSaved = Review.of(
            23,
            4,
            "great reading",
            User.of(21, "Name")
        );

    final var review = Review.of(
        null,
        4,
        "great reading",
        User.of(21, "Name")
    );

    when(reviewAdapterRest.toDomain(reviewDto))
        .thenReturn(review);

    when(creatorReviewUseCase.create(bookId, review))
        .thenReturn(reviewSaved);

    when(reviewAdapterRest.toDto(reviewSaved))
        .thenReturn(reviewDtoResponse);

    final var result = bookController.postReview(bookId, reviewDto);

    assertThat(result.id()).isEqualTo(23);
  }

  @Test
  void shouldReturnAListOfReviewsWithSuccess() {
    final var bookId = Integer.valueOf(12);
    final var review1 = Review.of(23,
        4,
        "great reading",
        User.of(21, "Name"));
    final var review2 = Review.of(24,
        4,
        "great reading",
        User.of(21, "Name"));

    final var reviewDto1 = ReviewDto.builder()
        .id(23)
        .score(4)
        .comment("great reading")
        .user(UserDto.builder()
            .id(21)
            .name("Name")
            .build())
        .build();

    final var reviewDto2 = ReviewDto.builder()
        .id(24)
        .score(4)
        .comment("great reading")
        .user(UserDto.builder()
            .id(21)
            .name("Name")
            .build())
        .build();


    when(getterReviewUseCase.findByBook(eq(bookId)))
        .thenReturn(List.of(review1,review2));

    when(reviewAdapterRest.toDto(review1))
        .thenReturn(reviewDto1);

    when(reviewAdapterRest.toDto(review2))
        .thenReturn(reviewDto2);

    final var result = bookController.getAllReview(bookId);

    assertThat(result).hasSize(2);
    assertThat(result.get(0).id()).isEqualTo(23);
    assertThat(result.get(1).id()).isEqualTo(24);
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

    assertThat(result.score()).isEqualTo(4.0);
    assertThat(result.book().id()).isEqualTo(12);
    assertThat(result.comments()).hasSize(1);
    assertThat(result.comments().get(0).comment()).isEqualTo("comment1");
  }

}