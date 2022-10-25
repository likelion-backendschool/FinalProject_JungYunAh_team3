package com.ll.exam.final__2022_10_08.app.order.controller;

import com.ll.exam.final__2022_10_08.app.base.rq.Rq;
import com.ll.exam.final__2022_10_08.app.order.dto.OrderDto;
import com.ll.exam.final__2022_10_08.app.order.entity.Order;
import com.ll.exam.final__2022_10_08.app.order.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

  private final OrderService orderService;

  @PostMapping("/create")
  @PreAuthorize("isAuthenticated()")
  public String createOrder() {

    Order order = orderService.createOrder();
    return Rq.redirectWithMsg("/order/" + order.getId(),
        "%d번 주문이 생성되었습니다.".formatted(order.getId()));
  }

  @GetMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public String showOrder(@PathVariable Long id, Model model) {
    OrderDto order = orderService.findById(id);
    model.addAttribute("order", order);
    return "order/detail";
  }

  @GetMapping("/list")
  @PreAuthorize("isAuthenticated()")
  public String showList(Model model) {
    List<Order> orderList = orderService.findAll();
    model.addAttribute("orderList", orderList);
    return "order/list";
  }
}
