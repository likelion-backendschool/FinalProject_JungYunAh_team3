package com.ll.exam.final__2022_10_08.app.cart.controller;

import com.ll.exam.final__2022_10_08.app.cart.dto.CartItemDto;
import com.ll.exam.final__2022_10_08.app.cart.service.CartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
@Slf4j
public class CartController {

  private final CartService cartService;

  @PostMapping("/add/{productId}")
  @PreAuthorize("isAuthenticated()")
  public String addCartItem(@PathVariable Long productId) {
    cartService.addCartItem(productId);
    return "redirect:/product/list";
  }

  @GetMapping("/list")
  @PreAuthorize("isAuthenticated()")
  public String list(Model model) {
    List<CartItemDto> cartItemList = cartService.findAll();
    model.addAttribute("cartItemList", cartItemList);
    return "cart/list";
  }
}
