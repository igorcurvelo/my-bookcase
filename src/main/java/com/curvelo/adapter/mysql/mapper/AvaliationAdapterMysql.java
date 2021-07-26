package com.curvelo.adapter.mysql.mapper;

import com.curvelo.core.domain.AvaliationDomain;
import com.curvelo.database.model.AvaliationModel;
import java.util.Optional;

public class AvaliationAdapterMysql {

  private AvaliationAdapterMysql() {}

  public static AvaliationModel toEntity(final AvaliationDomain avaliationDomain) {
    return Optional.ofNullable(avaliationDomain)
        .map(dto ->
            AvaliationModel.builder()
                .id(dto.getId())
                .userModel(UserAdapterMysql.toEntity(dto.getUser()))
                .score(dto.getScore())
                .comment(dto.getComment()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

  public static AvaliationDomain toDomain(final AvaliationModel avaliationModel) {
    return Optional.ofNullable(avaliationModel)
        .map(entity ->
            AvaliationDomain.builder()
                .id(entity.getId())
                .book(BookAdapterMysql.toDomain(avaliationModel.getBookModel()))
                .user(UserAdapterMysql.toDomain(avaliationModel.getUserModel()))
                .score(entity.getScore())
                .comment(entity.getComment()).build()
        ).orElseThrow(IllegalArgumentException::new);
  }

}
