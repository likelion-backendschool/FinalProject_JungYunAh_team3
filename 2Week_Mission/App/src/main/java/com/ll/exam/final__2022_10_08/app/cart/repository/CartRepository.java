package com.ll.exam.final__2022_10_08.app.cart.repository;

import com.ll.exam.final__2022_10_08.app.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartItem, Long> {

}
