package com.ll.exam.final__2022_10_08.app.order.service;

import com.ll.exam.final__2022_10_08.app.base.rq.Rq;
import com.ll.exam.final__2022_10_08.app.cart.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cart.service.CartService;
import com.ll.exam.final__2022_10_08.app.cashLog.service.CashLogService;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.member.service.MemberService;
import com.ll.exam.final__2022_10_08.app.myBook.service.MyBookService;
import com.ll.exam.final__2022_10_08.app.order.dto.OrderDto;
import com.ll.exam.final__2022_10_08.app.order.entity.Order;
import com.ll.exam.final__2022_10_08.app.order.entity.OrderItem;
import com.ll.exam.final__2022_10_08.app.order.entity.OrderStatus;
import com.ll.exam.final__2022_10_08.app.order.repository.OrderRepository;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

  private final OrderRepository orderRepository;
  private final CartService cartService;
  private final Rq rq;
  private final MyBookService myBookService;
  private final CashLogService cashLogService;
  private final MemberService memberService;

  @Transactional
  public Order createOrder() {

    List<CartItem> cartItems = cartService.getCartItemList(rq.getMember());

    List<OrderItem> orderItems = new ArrayList<>();

    for (CartItem cartItem : cartItems) {
      Product product = cartItem.getProduct();

      if (product.isOrderable()) {
        orderItems.add(new OrderItem(product));
      }

      cartService.removeCartItem(product.getId());
    }

    return create(orderItems);
  }

  @Transactional
  public Order create(List<OrderItem> orderItems) {
    Order order = Order
        .builder()
        .member(rq.getMember())
        .readyStatus(OrderStatus.READY)
        .build();

    for (OrderItem orderItem : orderItems) {
      order.addOrderItem(orderItem);
    }

    // 주문 품목으로 부터 이름을 만든다.
    order.makeName();

    orderRepository.save(order);

    return order;
  }

  public OrderDto findById(Long id) {
    Order order = orderRepository.findById(id).orElseThrow(null);
    List<OrderItem> orderItems = order.getOrderItems();
    int calculatePayPrice = order.calculatePayPrice();
    return OrderDto.builder()
        .id(order.getId())
        .createDate(order.getCreateDate())
        .member(rq.getMember().getUsername())
        .orderItems(orderItems)
        .calculatePayPrice(calculatePayPrice)
        .name(order.getName())
        .isPayable(order.isPayable())
        .isPaid(order.isPaid())
        .readyStatus(String.valueOf(order.getReadyStatus()))
        .build();
  }

  public List<Order> findAll() {
    return orderRepository.findAllByMemberId(rq.getMember().getId());
  }

  public void cancelOrder(Long id) {
    Order order = orderRepository.findById(id).orElseThrow(null);
    if (order.getReadyStatus().equals(OrderStatus.READY)) {
      orderRepository.delete(order);
    }
  }

  public Order findOrder(long id) {
    return orderRepository.findById(id).orElseThrow(null);
  }

  public void successOrder(Order order) {
    order.setReadyStatus(OrderStatus.DONE);
    order.setPaymentDone();
    Order savedOrder = orderRepository.save(order);
    myBookService.save(savedOrder.getOrderItems());
    cashLogService.successOrder(order.calculatePayPrice());
  }

  public boolean actorCanPayment(Order order) {
    return rq.getMember().getId().equals(order.getMember().getId());
  }

  public void payByRestCashOnly(Order order) {
    Member buyer = order.getMember();

    long restCash = buyer.getRestCash();
    int payPrice = order.calculatePayPrice();

    if (payPrice > restCash) {
      throw new RuntimeException("예치금이 부족합니다.");
    }

    memberService.addCash(buyer, payPrice * -1);

    order.setPaymentDone();
    orderRepository.save(order);
  }
}
