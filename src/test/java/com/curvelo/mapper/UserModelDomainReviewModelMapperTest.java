package com.curvelo.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.curvelo.adapter.input.restcontroller.dto.UserDto;
import org.junit.jupiter.api.Test;

class UserModelDomainReviewModelMapperTest {

  @Test
  void shouldMapperUserAvaliation() {

    var comment = "uma boa leitura";

    var user = UserDto.builder()
        .id(99)
        .name("Igor")
        .build();

    var result = UserReviewMapper.toDto(comment, user);

    assertThat(result.getUser().getId()).isEqualTo(99);
    assertThat(result.getUser().getName()).isEqualTo("Igor");
    assertThat(result.getComment()).isEqualTo("uma boa leitura");
  }

}