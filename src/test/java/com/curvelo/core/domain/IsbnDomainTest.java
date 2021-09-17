package com.curvelo.core.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IsbnDomainTest {

  @Test
  void shouldCreateIsbnValid() {

//    IsbnDomain.from("978-85-7827-069-8");

    IsbnDomain.from("978-0-306-40615-7");

  }

}