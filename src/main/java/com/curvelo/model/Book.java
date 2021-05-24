package com.curvelo.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "book")
@Getter
@Builder
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String author;

  private Integer numberOfPages;

  @CreatedDate
  private LocalDateTime createdDate;

}
