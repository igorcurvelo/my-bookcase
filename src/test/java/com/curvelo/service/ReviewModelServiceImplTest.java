package com.curvelo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.curvelo.database.model.BookModel;
import com.curvelo.database.model.ReviewModel;
import com.curvelo.database.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewModelServiceImplTest {

  @Mock
  private ReviewRepository reviewRepository;

  @Mock
  private BookService bookService;

  @InjectMocks
  private ReviewServiceImpl reviewService;

  @Test
  void shouldCreateAReviewWithSuccess() {

    var book = BookModel.builder()
        .id(1)
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

    when(bookService.findOne(1)).thenReturn(book);
    when(reviewRepository.save(any(ReviewModel.class)))
        .thenReturn(ReviewModel.builder()
          .id(2)
          .book(book)
          .comment("Um bom livro")
          .score(3)
          .build());

    var result = reviewService.create(1,
        ReviewModel.builder()
          .comment("Um bom livro")
          .score(3)
          .build());

    assertThat(result.getId()).isNotNull();
    assertThat(result.getComment()).isEqualTo("Um bom livro");
    assertThat(result.getScore()).isEqualTo(3);
    assertThat(result.getBook().getId()).isEqualTo(1);
    assertThat(result.getBook().getAuthor()).isEqualTo("J.R.R. Tolkien");
    assertThat(result.getBook().getTitle()).isEqualTo("Hobbit");
    assertThat(result.getBook().getNumberOfPages()).isEqualTo(250);
    assertThat(result.getBook().getIsbn()).isEqualTo("123456789");

    verify(bookService, times(1)).findOne(1);
    verify(reviewRepository, times(1)).save(any(ReviewModel.class));
  }
}