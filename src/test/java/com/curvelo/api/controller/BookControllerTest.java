package com.curvelo.api.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.curvelo.MyBookcaseApplication;
import com.curvelo.api.dto.AvaliationDTO;
import com.curvelo.domain.model.Avaliation;
import com.curvelo.domain.model.Book;
import com.curvelo.repository.AvaliationRepository;
import com.curvelo.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = {MyBookcaseApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class BookControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private AvaliationRepository avaliationRepository;

  private MockMvc mockMvc;

  private final Faker faker = new Faker();

  @Before
  public void before() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    this.avaliationRepository.deleteAll();
    this.bookRepository.deleteAll();
  }

  @Test
  public void shouldReturnAListOfBookWithSuccess() throws Exception {
    var book1 = createBook();
    createBook();
    createBook();

    this.mockMvc.perform(get("/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].id", is(book1.getId())))
        .andExpect(jsonPath("$[0].author", is(book1.getAuthor())))
        .andExpect(jsonPath("$[0].title", is(book1.getTitle())))
        .andExpect(jsonPath("$[0].isbn", is(book1.getIsbn())))
        .andExpect(jsonPath("$[0].numberOfPages", is(book1.getNumberOfPages())));
  }

  @Test
  public void shouldCreateABookWithSuccess() throws Exception {
    var book = Book.builder()
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

    this.mockMvc.perform(post("/books")
        .content(toJson(book))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.title", is("Hobbit")))
        .andExpect(jsonPath("$.author", is("J.R.R. Tolkien")))
        .andExpect(jsonPath("$.isbn", is("123456789")))
        .andExpect(jsonPath("$.numberOfPages", is(250)));

    this.mockMvc.perform(get("/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", notNullValue()))
        .andExpect(jsonPath("$[0].title", is("Hobbit")))
        .andExpect(jsonPath("$[0].author", is("J.R.R. Tolkien")))
        .andExpect(jsonPath("$[0].isbn", is("123456789")))
        .andExpect(jsonPath("$[0].numberOfPages", is(250)));
  }

  @Test
  public void shouldCreateAAvaliationWithSuccess() throws Exception {
    var book1 = createBook();

    var avaliation = AvaliationDTO.builder()
        .comment("Um bom livro")
        .score(3)
        .build();

    this.mockMvc.perform(post("/books/"+book1.getId()+"/avaliation")
        .content(toJson(avaliation))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.score", is(3)))
        .andExpect(jsonPath("$.comment", is("Um bom livro")));
  }

  @Test
  public void shouldReturnAAvaliationByBookWithSuccess() throws Exception {
    var book = createBook();

    var avaliation = Avaliation.builder()
        .book(book)
        .comment("Um bom livro")
        .score(3)
        .build();

    avaliationRepository.save(avaliation);

    this.mockMvc.perform(get("/books/"+book.getId()+"/avaliation")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.book.id", notNullValue()))
        .andExpect(jsonPath("$.score", is(3)))
        .andExpect(jsonPath("$.comment", is("Um bom livro")));
  }

  private Book createBook() {
    return bookRepository.save(Book.builder()
        .author(faker.book().author())
        .title(faker.book().title())
        .isbn(String.valueOf(faker.number().numberBetween(1, 9999)))
        .numberOfPages(faker.number().numberBetween(100, 300)).build());
  }

  private String toJson(Object value) {
    try {
      return new ObjectMapper().writeValueAsString(value);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return "Json invalid";
  }
}