package com.curvelo.core.domain;

import java.util.Optional;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class IsbnDomain {

  private String value;

  public static IsbnDomain from(final String isbn) {
    return new IsbnDomain(isbn);
  }

  private IsbnDomain(final String value) {
    validate(value);

    this.value = value;
  }

  private void validate(final String value) {
    var isbn = Optional.ofNullable(value)
        .map(v -> v.replaceAll("-", ""))
        .orElseThrow(() -> new IllegalArgumentException("ISBN is invalid"));

    if (isbn.length() != 13) {
      throw new IllegalArgumentException("ISBN must have 13 characters");
    }

    if (!StringUtils.isNumeric(isbn)) {
      throw new IllegalArgumentException("ISBN must be a number");
    }

    int sum = 0;
    for (int i = 0; i < isbn.length() - 1; i++) {
      var multiplier = i % 2 == 0 ? 1 : 3;
      sum += Character.getNumericValue(isbn.charAt(i)) * multiplier;
    }

    var digit = (sum + Character.getNumericValue(isbn.charAt(isbn.length() - 1))) % 10;

    if (digit != 0) {
      throw new IllegalArgumentException("ISBN invalid");
    }

  }
}
