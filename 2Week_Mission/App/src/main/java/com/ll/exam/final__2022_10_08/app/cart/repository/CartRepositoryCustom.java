package com.ll.exam.final__2022_10_08.app.cart.repository;

import com.ll.exam.final__2022_10_08.app.cart.dto.CartItemDto;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import java.util.List;

public interface CartRepositoryCustom {

  List<CartItemDto> findCartItem(Member member);
}
