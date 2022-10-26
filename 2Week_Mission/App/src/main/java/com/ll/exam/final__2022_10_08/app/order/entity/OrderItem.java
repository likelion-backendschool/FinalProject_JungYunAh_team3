package com.ll.exam.final__2022_10_08.app.order.entity;

import static javax.persistence.FetchType.LAZY;

import com.ll.exam.final__2022_10_08.app.base.entity.BaseEntity;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/*
주문 품목
- orderId, 주문 번호
- productId, 도서 번호
- payDate, 결제 날짜
- price, 가격
- salePrice, 실제 판매가
- wholesalePrice, 도매가
- pgFree, 결제대행사 수수료
- payPrice, 결제금액
- refundPrice, 환불금액
- isPaid, 결제여부
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class OrderItem extends BaseEntity {

  @ManyToOne(fetch = LAZY)
  @ToString.Exclude
  private Order order;

  @ManyToOne(fetch = LAZY)
  private Product product;

  private LocalDateTime payDate;
  private int price;
  private int salePrice;
  private int wholesalePrice;
  private int pgFee;
  private int payPrice;
  private int refundPrice;
  private boolean isPaid;

  public OrderItem(Product product) {
    this.product = product;
    this.price = product.getPrice();
    this.salePrice = product.getSalePrice();
    this.wholesalePrice = product.getWholesalePrice();
  }

  public void setPaymentDone() {
    this.pgFee = 0;
    this.payPrice = getSalePrice();
    this.isPaid = true;
    this.payDate = LocalDateTime.now();
  }
}
