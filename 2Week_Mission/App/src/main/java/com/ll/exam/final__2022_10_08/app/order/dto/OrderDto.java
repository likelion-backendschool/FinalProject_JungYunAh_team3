package com.ll.exam.final__2022_10_08.app.order.dto;

import com.ll.exam.final__2022_10_08.app.order.entity.OrderItem;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class OrderDto {

  private Long id;
  private LocalDateTime createDate;
  private String member;
  private int calculatePayPrice;
  private String name;
  private List<OrderItem> orderItems;
  private boolean isPayable;
  private boolean isPaid;
  private String readyStatus;
}
