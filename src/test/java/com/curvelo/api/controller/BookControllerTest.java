package com.curvelo.api.controller;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import com.curvelo.api.dto.BookDTO;
import com.curvelo.api.dto.ReviewDTO;
import com.curvelo.database.model.BookModel;
import com.curvelo.database.model.ReviewModel;
import com.curvelo.database.model.UserModel;
import com.curvelo.database.repository.BookRepository;
import com.curvelo.database.repository.ReviewRepository;
import com.curvelo.database.repository.UserRepository;
import com.curvelo.mapper.UserMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BookControllerTest {

  private final Faker faker = new Faker();

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ReviewRepository reviewRepository;

  @LocalServerPort
  int serverPort;

  @BeforeEach
  public void setup() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = serverPort;

    this.reviewRepository.deleteAll();
    this.bookRepository.deleteAll();
  }

  @Test
  void shouldReturnAListOfBookWithSuccess() {
    var book1 = bookRepository.save(BookModel.builder()
        .isbn("9788533615540")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit").build());

    bookRepository.save(BookModel.builder()
        .isbn("9788533613393")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("O Retorno do Rei").build());

    bookRepository.save(BookModel.builder()
        .isbn("9788533613379")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("A Sociedade do Anel").build());

    when()
        .get("/books")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("$", hasSize(3))
        .body("[0].id", equalTo(book1.getId()))
        .body("[0].authors[0]", equalTo(book1.getAuthor()))
        .body("[0].title", equalTo(book1.getTitle()))
        .body("[0].isbn", equalTo(book1.getIsbn()))
        .body("[0].numberOfPages", equalTo(book1.getNumberOfPages()));
  }

  @Test
  void shouldCreateABookWithSuccess() {
    var book = BookDTO.builder()
        .isbn("9788533615540")
        .numberOfPages(250)
        .authors(List.of("J.R.R. Tolkien"))
        .title("Hobbit")
        .build();

    given()
        .contentType(ContentType.JSON)
        .body(book)
      .when()
        .post("/books")
      .then()
        .statusCode(HttpStatus.CREATED.value())
        .body("id", notNullValue())
        .body("authors[0]", equalTo("J.R.R. Tolkien"))
        .body("title", equalTo("Hobbit"))
        .body("isbn", equalTo("9788533615540"))
        .body("numberOfPages", equalTo(250));

    when()
        .get("/books")
      .then()
        .statusCode(HttpStatus.OK.value())
        .body("$", hasSize(1))
        .body("[0].id", notNullValue())
        .body("[0].title", equalTo("Hobbit"))
        .body("[0].authors[0]", equalTo("J.R.R. Tolkien"))
        .body("[0].isbn", equalTo("9788533615540"))
        .body("[0].numberOfPages", equalTo(250));
  }

//  @Test verificar como fica o handler de exception
  void shouldReturnHttpStatus409WhenIsbnAlreadyExist() {
    bookRepository.save(BookModel.builder()
        .isbn("9788533615540")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit").build());

    var book = BookDTO.builder()
        .isbn("9788533615540")
        .numberOfPages(250)
        .authors(List.of("J.R.R. Tolkien"))
        .title("Hobbit")
        .build();

    given()
        .contentType(ContentType.JSON)
        .body(book)
        .when()
        .post("/books")
        .then()
        .statusCode(HttpStatus.CONFLICT.value());
  }

  @Test
  void shouldCreateAReviewWithSuccess() {
    var book1 = createBook();
    var user = createUser();

    var review = ReviewDTO.builder()
        .comment("Um bom livro")
        .user(UserMapper.toDTO(user))
        .score(3)
        .build();

    given()
        .contentType(ContentType.JSON)
        .body(review)
      .when()
        .post("/books/{id}/reviews", book1.getId())
      .then()
        .statusCode(HttpStatus.CREATED.value())
        .body("id", notNullValue())
        .body("user.id", equalTo(user.getId()))
        .body("book.id", equalTo(book1.getId()))
        .body("score", equalTo(3))
        .body("comment", equalTo("Um bom livro"));
  }

  @Test
  void shouldReturnAReviewByBookWithSuccess() {
    var book = createBook();
    var user = createUser();

    var review = ReviewModel.builder()
        .book(book)
        .user(user)
        .comment("Um bom livro")
        .score(3)
        .build();

    reviewRepository.save(review);

    when()
        .get("/books/{id}/reviews", book.getId())
      .then()
        .contentType(ContentType.JSON)
        .statusCode(HttpStatus.OK.value())
        .body("[0].id", notNullValue())
        .body("[0].book.id", equalTo(book.getId()))
        .body("[0].user.id", equalTo(user.getId()))
        .body("[0].score", equalTo(3))
        .body("[0].comment", equalTo("Um bom livro"));
  }

  @Test
  void shouldReturnCalculateReviewByBookWithSuccess() {
    var book = createBook();
    var user1 = createUser();
    var user2 = createUser();

    var review1 = ReviewModel.builder()
        .book(book)
        .user(user1)
        .comment("Um bom livro")
        .score(3)
        .build();

    var review2 = ReviewModel.builder()
        .book(book)
        .user(user2)
        .comment("Ótima leitura")
        .score(4)
        .build();

    reviewRepository.save(review1);
    reviewRepository.save(review2);

    when()
        .get("/books/{id}/reviews/calculate", book.getId())
        .then()
        .contentType(ContentType.JSON)
        .statusCode(HttpStatus.OK.value())
        .body("book.id", equalTo(book.getId()))
        .body("book.title", equalTo(book.getTitle()))
        .body("score", equalTo(3.5F))
        .body("comments", hasSize(2))
        .body("comments[0].user.id", equalTo(user1.getId()))
        .body("comments[0].comment", equalTo(review1.getComment()))
        .body("comments[1].user.id", equalTo(user2.getId()))
        .body("comments[1].comment", equalTo(review2.getComment()));
  }

  private UserModel createUser() {
    return userRepository.save(UserModel.builder()
        .id(99)
        .name(faker.name().firstName())
        .build());
  }

  private BookModel createBook() {
    return bookRepository.save(BookModel.builder()
        .author(faker.book().author())
        .title(faker.book().title())
        .isbn("9788533615540")
        .numberOfPages(faker.number().numberBetween(100, 300)).build());
  }
}