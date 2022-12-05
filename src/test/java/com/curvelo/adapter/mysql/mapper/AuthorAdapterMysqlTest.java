package com.curvelo.adapter.mysql.mapper;

import static com.curvelo.constant.Constants.SEPARATOR_AUTHOR_MODEL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.curvelo.core.domain.Author;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthorAdapterMysqlTest {

  @InjectMocks
  private AuthorAdapterMysql authorAdapterMysql;

  @Test
  void shouldMapperAuthorModelWithOnlyOneAuthorToListAuthorDomain() {
    final var result = authorAdapterMysql.toDomain("J.R.R. Tolkien");

    assertThat(result).hasSize(1);
    assertThat(result.get(0).getName()).isEqualTo("J.R.R. Tolkien");
  }

  @Test
  void shouldMapperAuthorModelWithThreeAuthorsToListAuthorDomain() {
    final var authors = "Geoffrey G. Parker" + SEPARATOR_AUTHOR_MODEL + "Marshall W. Van Alstyne"
        + SEPARATOR_AUTHOR_MODEL + "Sangeet Paul Choudary";
    final var result = authorAdapterMysql.toDomain(authors);

    assertThat(result).hasSize(3);
    assertThat(result.get(0).getName()).isEqualTo("Geoffrey G. Parker");
    assertThat(result.get(1).getName()).isEqualTo("Marshall W. Van Alstyne");
    assertThat(result.get(2).getName()).isEqualTo("Sangeet Paul Choudary");
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenAuthorModelIsNull() {
    assertThatThrownBy(() -> authorAdapterMysql.toDomain(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Author is required");
  }

  @Test
  void shouldReturnIllegalArgumentExceptionWhenAuthorModelIsEmpty() {
    assertThatThrownBy(() -> authorAdapterMysql.toDomain(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Author is required");
  }

  @Test
  void shouldMapperAuthorDomainToAuthorModel() {
    final var authors = List.of(Author.of("J.R.R. Tolkien"), Author.of("Geoffrey G. Parker"));
    final var result = authorAdapterMysql.toModel(authors);

    assertThat(result).isEqualTo("J.R.R. Tolkien" + SEPARATOR_AUTHOR_MODEL + "Geoffrey G. Parker");
  }

}