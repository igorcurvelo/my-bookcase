package com.curvelo.adapter.mysql.mapper;

import com.curvelo.core.domain.Author;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.curvelo.constant.Constants.SEPARATOR_AUTHOR_MODEL;

public class AuthorAdapterMysql {

  private AuthorAdapterMysql() {}

  public static List<Author> toDomain(final String authorModel) {
    if (Objects.isNull(authorModel) || authorModel.trim().isEmpty()) {
      throw new IllegalArgumentException("Author is required");
    }

    return Optional.of(authorModel)
        .map(authors -> Arrays.stream(authors.split(SEPARATOR_AUTHOR_MODEL))
            .map(Author::of).collect(Collectors.toList()))
        .orElseThrow(() -> new IllegalArgumentException("Error on author"));
  }

  public static String toModel(List<Author> authors) {
    return authors.stream().map(Author::getName).collect(
        Collectors.joining(SEPARATOR_AUTHOR_MODEL));
  }
}