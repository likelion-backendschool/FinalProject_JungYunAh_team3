package com.ll.exam.final__2022_10_08.app.cart.dto;

import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class CartItemDto {

  private Long id;
  private String jdenticon;
  private String subject;
  private int price;
  private String author;
  public static CartItemDto from(Member member, Product product) {

    return CartItemDto.builder()
        .id(product.getId())
        .jdenticon(product.getJdenticon())
        .subject(product.getSubject())
        .price(product.getPrice())
        .author(member.getUsername())
        .build();
  }
}
