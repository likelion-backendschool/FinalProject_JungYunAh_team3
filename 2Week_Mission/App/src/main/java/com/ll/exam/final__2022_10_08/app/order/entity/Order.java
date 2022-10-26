package com.ll.exam.final__2022_10_08.app.order.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import com.ll.exam.final__2022_10_08.app.base.entity.BaseEntity;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/*
주문
- memberId, 회원 번호
- payDate, 결제 날짜
- readyStatus, 주문완료여부
- isPaid, 결제완료여부
- isCanceled, 취소여부
- isRefunded, 환불여부
- name, 주문명
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Table(name = "product_order")
public class Order extends BaseEntity {

  @ManyToOne(fetch = LAZY)
  private Member member;

  private LocalDateTime payDate;

  @Enumerated(EnumType.STRING)
  private OrderStatus readyStatus;
  private boolean isPaid;
  private boolean isCanceled;
  private boolean isRefunded;
  private String name;

  @Builder.Default
  @OneToMany(mappedBy = "order", cascade = ALL, orphanRemoval = true)
  private List<OrderItem> orderItems = new ArrayList<>();

  public void makeName() {
    String name = orderItems.get(0).getProduct().getSubject();

    if (orderItems.size() > 1) {
      name += " 외 %d권".formatted(orderItems.size() - 1);
    }

    this.name = name;
  }

  public void addOrderItem(OrderItem orderItem) {
    orderItem.setOrder(this);
    orderItems.add(orderItem);
  }

  public int calculatePayPrice() {
    int payPrice = 0;

    for (OrderItem orderItem : orderItems) {
      payPrice += orderItem.getSalePrice();
    }

    return payPrice;
  }

  public void setPaymentDone() {
    for (OrderItem orderItem : orderItems) {
      orderItem.setPaymentDone();
    }

    isPaid = true;
  }
}
