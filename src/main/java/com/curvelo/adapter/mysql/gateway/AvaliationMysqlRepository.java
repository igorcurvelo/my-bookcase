package com.curvelo.adapter.mysql.gateway;

import com.curvelo.adapter.mysql.mapper.AvaliationAdapterMysql;
import com.curvelo.core.domain.AvaliationDomain;
import com.curvelo.core.repository.AvaliationDomainRepository;
import com.curvelo.database.repository.AvaliationRepository;
import java.util.List;
import java.util.stream.Collectors;

public class AvaliationMysqlRepository implements AvaliationDomainRepository {

  private final AvaliationRepository avaliationRepository;

  public AvaliationMysqlRepository(
      AvaliationRepository avaliationRepository) {
    this.avaliationRepository = avaliationRepository;
  }

  @Override
  public List<AvaliationDomain> findByBookId(int bookId) {
    return avaliationRepository.findByBookId(bookId)
        .stream().map(AvaliationAdapterMysql::toDomain)
        .collect(Collectors.toList());
  }
}
