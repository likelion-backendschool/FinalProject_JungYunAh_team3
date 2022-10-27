package com.ll.exam.final__2022_10_08.app.cart.entity;

import static javax.persistence.FetchType.LAZY;

import com.ll.exam.final__2022_10_08.app.base.entity.BaseEntity;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class CartItem extends BaseEntity {

  @ManyToOne(fetch = LAZY)
  private Member member;

  @ManyToOne(fetch = LAZY)
  private Product product;
}
