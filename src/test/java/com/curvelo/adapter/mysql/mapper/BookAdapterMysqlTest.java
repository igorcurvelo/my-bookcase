package com.curvelo.adapter.mysql.mapper;

import com.curvelo.database.model.BookModel;

class BookAdapterMysqlTest {

  // @Test todo ajustar teste
  void shouldMapperBookModelToBookDomain() {
    var book = BookModel.builder()
        .id(12)
        .isbn("123456789")
        .numberOfPages(250)
        .author("J.R.R. Tolkien")
        .title("Hobbit")
        .build();

//    var result = BookAdapterMysql.toDomain(book, reviews);
//
//    assertThat(result.getId()).isEqualTo(12);
//    assertThat(result.getIsbn()).isEqualTo("123456789");
//    assertThat(result.getNumberOfPages()).isEqualTo(250);
//    assertThat(result.getAuthor()).isEqualTo("J.R.R. Tolkien");
//    assertThat(result.getTitle()).isEqualTo("Hobbit");
  }

}