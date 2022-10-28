package com.ll.exam.final__2022_10_08.app.myBook.repository;

import static com.ll.exam.final__2022_10_08.app.myBook.entity.QMyBook.myBook;
import static com.ll.exam.final__2022_10_08.app.product.entity.QProduct.product;

import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.myBook.dto.MyBookDto;
import com.ll.exam.final__2022_10_08.app.myBook.entity.MyBook;
import com.ll.exam.final__2022_10_08.app.myBook.entity.QMyBook;
import com.ll.exam.final__2022_10_08.app.product.entity.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MyBookRepositoryImpl implements MyBookRepositoryCustom{

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<MyBookDto> findMyBook(Member member) {
    List<MyBook> myBookList = jpaQueryFactory
        .select(myBook)
        .from(myBook)
        .innerJoin(myBook.product, product).fetchJoin()
        .where(myBook.member.eq(member))
        .fetch();

    return myBookList.stream().map(myBook -> MyBookDto.from(myBook.getProduct())).collect(Collectors.toList());
  }
}
