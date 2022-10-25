package com.ll.exam.final__2022_10_08.app.cart.repository;

import com.ll.exam.final__2022_10_08.app.cart.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartItem, Long> {
  Optional<CartItem> findByProduct(Product product);

  List<CartItem> findAllByMember(Member member);
}
