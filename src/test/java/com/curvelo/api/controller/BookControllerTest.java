package com.curvelo.api.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.curvelo.MyBookcaseApplication;
import com.curvelo.model.Book;
import com.curvelo.repository.BookRepository;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

  private MockMvc mockMvc;

  private final Faker faker = new Faker();

  @Before
  public void before() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void shouldCreateABookWithSuccess() throws Exception {
    var book1 = createBook();
    var book2 = createBook();
    var book3 = createBook();

    this.mockMvc.perform(get("/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].id", is(book1.getId())))
        .andExpect(jsonPath("$[0].author", is(book1.getAuthor())))
        .andExpect(jsonPath("$[0].title", is(book1.getTitle())))
        .andExpect(jsonPath("$[0].isbn", is(book1.getIsbn())))
        .andExpect(jsonPath("$[0].numberOfPages", is(book1.getNumberOfPages())));
  }

  private Book createBook() {
    return bookRepository.save(Book.builder()
        .author(faker.book().author())
        .title(faker.book().title())
        .isbn(String.valueOf(faker.number().numberBetween(1, 9999)))
        .numberOfPages(faker.number().numberBetween(100, 300)).build());
  }
}