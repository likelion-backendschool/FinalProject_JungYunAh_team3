package com.ll.exam.final__2022_10_08.app.cart.service;

import com.ll.exam.final__2022_10_08.app.base.rq.Rq;
import com.ll.exam.final__2022_10_08.app.cart.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cart.repository.CartRepository;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import com.ll.exam.final__2022_10_08.app.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

  private final ProductRepository productRepository;
  private final CartRepository cartRepository;
  private final Rq rq;

  public void addCartItem(Long productId) {
    Product product = productRepository.findById(productId).orElseThrow(null);

    CartItem cartItem = CartItem.builder()
        .member(rq.getMember())
        .product(product)
        .build();

    cartRepository.save(cartItem);
  }
}
