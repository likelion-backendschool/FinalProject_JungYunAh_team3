package com.ll.exam.final__2022_10_08.app.order.service;

import com.ll.exam.final__2022_10_08.app.base.rq.Rq;
import com.ll.exam.final__2022_10_08.app.cart.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cart.service.CartService;
import com.ll.exam.final__2022_10_08.app.order.entity.Order;
import com.ll.exam.final__2022_10_08.app.order.entity.OrderItem;
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
  public Order createOrder() {

    List<CartItem> cartItemList = cartService.getCartItemList(rq.getMember());
    List<OrderItem> orderItemList = new ArrayList<>();

    for (CartItem cartItem : cartItemList) {
      Product product = cartItem.getProduct();
      orderItemList.add(new OrderItem(product));
      cartService.removeCartItem(product.getId());
    }

    Order order = Order.builder()
        .orderItems(orderItemList)
        .member(rq.getMember())
        .build();
    order.makeName();
    return orderRepository.save(order);
  }

  public Order findById(Long id) {
    return orderRepository.findById(id).orElseThrow(null);
  }
}
