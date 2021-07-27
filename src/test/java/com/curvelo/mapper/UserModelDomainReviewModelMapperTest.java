package com.curvelo.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.curvelo.api.dto.UserDTO;
import org.junit.jupiter.api.Test;

class UserModelDomainReviewModelMapperTest {

  @Test
  void shouldMapperUserAvaliation() {

    var comment = "uma boa leitura";

    var user = UserDTO.builder()
        .id(99)
        .name("Igor")
        .build();

    var result = UserReviewMapper.toDTO(comment, user);

    assertThat(result.getUser().getId()).isEqualTo(99);
    assertThat(result.getUser().getName()).isEqualTo("Igor");
    assertThat(result.getComment()).isEqualTo("uma boa leitura");
  }

}