package com.curvelo.api.controller;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import com.curvelo.api.dto.AvaliationDTO;
import com.curvelo.domain.model.Avaliation;
import com.curvelo.domain.model.Book;
import com.curvelo.repository.AvaliationRepository;
import com.curvelo.repository.BookRepository;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BookControllerTest {

  private final Faker faker = new Faker();

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private AvaliationRepository avaliationRepository;

  @LocalServerPort
  int serverPort;

  @BeforeEach
  public void setup() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = serverPort;

    this.avaliationRepository.deleteAll();
    this.bookRepository.deleteAll();
  }

  @Test
  void shouldReturnAListOfBookWithSuccess() {
    var book1 = createBook();
    createBook();
    createBook();

    when()
        .get("/books")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("$", hasSize(3))
        .body("[0].id", equalTo(book1.getId()))
        .body("[0].author", equalTo(book1.getAuthor()))
        .body("[0].title", equalTo(book1.getTitle()))
        .body("[0].isbn", equalTo(book1.getIsbn()))
        .body("[0].numberOfPages", equalTo(book1.getNumberOfPages()));
  }

  @Test
  void shouldCreateABookWithSuccess() {
    var book = Book.builder()
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
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
        .body("author", equalTo("J.R.R. Tolkien"))
        .body("title", equalTo("Hobbit"))
        .body("isbn", equalTo("123456789"))
        .body("numberOfPages", equalTo(250));

    when()
        .get("/books")
      .then()
        .statusCode(HttpStatus.OK.value())
        .body("$", hasSize(1))
        .body("[0].id", notNullValue())
        .body("[0].title", equalTo("Hobbit"))
        .body("[0].author", equalTo("J.R.R. Tolkien"))
        .body("[0].isbn", equalTo("123456789"))
        .body("[0].numberOfPages", equalTo(250));
  }

  @Test
  void shouldCreateAnAvaliationWithSuccess() {
    var book1 = createBook();

    var avaliation = AvaliationDTO.builder()
        .comment("Um bom livro")
        .score(3)
        .build();

    given()
        .contentType(ContentType.JSON)
        .body(avaliation)
      .when()
        .post("/books/{id}/avaliation", book1.getId())
      .then()
        .statusCode(HttpStatus.CREATED.value())
        .body("id", notNullValue())
        .body("score", equalTo(3))
        .body("comment", equalTo("Um bom livro"));
  }

  @Test
  void shouldReturnAnAvaliationByBookWithSuccess() {
    var book = createBook();

    var avaliation = Avaliation.builder()
        .book(book)
        .comment("Um bom livro")
        .score(3)
        .build();

    avaliationRepository.save(avaliation);

    when()
        .get("/books/{id}/avaliation", book.getId())
      .then()
        .contentType(ContentType.JSON)
        .statusCode(HttpStatus.OK.value())
        .body("id", notNullValue())
        .body("book.id", notNullValue())
        .body("score", equalTo(3))
        .body("comment", equalTo("Um bom livro"));
  }

  private Book createBook() {
    return bookRepository.save(Book.builder()
        .author(faker.book().author())
        .title(faker.book().title())
        .isbn(String.valueOf(faker.number().numberBetween(1, 9999)))
        .numberOfPages(faker.number().numberBetween(100, 300)).build());
  }
}