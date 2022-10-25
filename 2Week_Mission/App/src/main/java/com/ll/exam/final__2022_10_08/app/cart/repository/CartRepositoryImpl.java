package com.ll.exam.final__2022_10_08.app.cart.repository;

import static com.ll.exam.final__2022_10_08.app.cart.entity.QCartItem.cartItem;
import static com.ll.exam.final__2022_10_08.app.product.entity.QProduct.product;

import com.ll.exam.final__2022_10_08.app.cart.dto.CartItemDto;
import com.ll.exam.final__2022_10_08.app.cart.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom{

  private final JPAQueryFactory jpaQueryFactory;


  @Override
  public List<CartItemDto> findCartItem(Member member) {
    List<CartItem> cartItemList = jpaQueryFactory
        .select(cartItem)
        .from(cartItem)
        .innerJoin(cartItem.product, product)
        .where(cartItem.member.eq(member))
        .fetch();

    List<CartItemDto> cartItemDtos = cartItemList.stream()
        .map(cartItem1 -> CartItemDto.from(cartItem1.getMember(), cartItem1.getProduct())).collect(
            Collectors.toList());

    return cartItemDtos;
  }
}
