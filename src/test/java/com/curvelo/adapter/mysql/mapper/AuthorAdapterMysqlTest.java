package com.curvelo.adapter.mysql.mapper;

import static com.curvelo.constant.Constants.SEPARATOR_AUTHOR_MODEL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class AuthorAdapterMysqlTest {

  @Test
  void shouldMapperAuthorModelWithOnlyOneAuthorToListAuthorDomain() {
    var result = AuthorAdapterMysql.toDomain("J.R.R. Tolkien");

    assertThat(result).hasSize(1);
    assertThat(result.get(0).getName()).isEqualTo("J.R.R. Tolkien");
  }

  @Test
  void shouldMapperAuthorModelWithThreeAuthorsToListAuthorDomain() {
    var authors = "Geoffrey G. Parker" + SEPARATOR_AUTHOR_MODEL + "Marshall W. Van Alstyne"
        + SEPARATOR_AUTHOR_MODEL + "Sangeet Paul Choudary";
    var result = AuthorAdapterMysql.toDomain(authors);

    assertThat(result).hasSize(3);
    assertThat(result.get(0).getName()).isEqualTo("Geoffrey G. Parker");
    assertThat(result.get(1).getName()).isEqualTo("Marshall W. Van Alstyne");
    assertThat(result.get(2).getName()).isEqualTo("Sangeet Paul Choudary");
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenAuthorModelIsNull() {
    assertThatThrownBy(() -> AuthorAdapterMysql.toDomain(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Author is required");
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenAuthorModelIsEmpty() {
    assertThatThrownBy(() -> AuthorAdapterMysql.toDomain(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Author is required");
  }

}