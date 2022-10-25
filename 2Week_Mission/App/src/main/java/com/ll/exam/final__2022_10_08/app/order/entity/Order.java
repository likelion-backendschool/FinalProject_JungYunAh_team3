package com.ll.exam.final__2022_10_08.app.order.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import com.ll.exam.final__2022_10_08.app.base.entity.BaseEntity;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Order extends BaseEntity {

  @ManyToOne(fetch = LAZY)
  private Member member;

  private LocalDateTime payDate;
  private boolean readyStatus;
  private boolean isPaid;
  private boolean isCanceled;
  private boolean isRefunded;
  private String name;

  @Builder.Default
  @OneToMany(mappedBy = "order", cascade = ALL, orphanRemoval = true)
  private List<OrderItem> orderItems = new ArrayList<>();

}
