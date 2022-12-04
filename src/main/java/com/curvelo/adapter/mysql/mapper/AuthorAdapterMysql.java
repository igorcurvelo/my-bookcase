package com.curvelo.adapter.mysql.mapper;

import static com.curvelo.constant.Constants.SEPARATOR_AUTHOR_MODEL;

import com.curvelo.core.domain.Author;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AuthorAdapterMysql {

  public List<Author> toDomain(final String authorModel) {
    if (Objects.isNull(authorModel) || authorModel.trim().isEmpty()) {
      throw new IllegalArgumentException("Author is required");
    }

    return Arrays.stream(authorModel.split(SEPARATOR_AUTHOR_MODEL))
        .map(Author::of).collect(Collectors.toList());
  }

  public String toModel(List<Author> authors) {
    return authors.stream().map(Author::getName).collect(
        Collectors.joining(SEPARATOR_AUTHOR_MODEL));
  }
}
