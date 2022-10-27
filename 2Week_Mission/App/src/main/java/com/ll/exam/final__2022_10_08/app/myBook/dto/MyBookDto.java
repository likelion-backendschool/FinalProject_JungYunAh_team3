package com.ll.exam.final__2022_10_08.app.myBook.dto;

import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class MyBookDto {
  private Long id;
  private String jdenticon;
  private String subject;
  private int price;
  private String author;

  public static MyBookDto from(Product product) {
    return MyBookDto.builder()
        .id(product.getId())
        .jdenticon(product.getJdenticon())
        .subject(product.getSubject())
        .price(product.getPrice())
        .author(product.getAuthor().getUsername())
        .build();
  }
}
