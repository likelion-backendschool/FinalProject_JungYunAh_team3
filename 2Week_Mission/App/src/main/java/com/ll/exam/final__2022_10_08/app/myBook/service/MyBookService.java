package com.ll.exam.final__2022_10_08.app.myBook.service;

import com.ll.exam.final__2022_10_08.app.base.rq.Rq;
import com.ll.exam.final__2022_10_08.app.myBook.dto.MyBookDto;
import com.ll.exam.final__2022_10_08.app.myBook.entity.MyBook;
import com.ll.exam.final__2022_10_08.app.myBook.repository.MyBookRepository;
import com.ll.exam.final__2022_10_08.app.order.entity.OrderItem;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MyBookService {

  private final MyBookRepository myBookRepository;
  private final Rq rq;

  public void save(List<OrderItem> orderItems) {
    for (OrderItem orderItem : orderItems) {
      MyBook myBook = MyBook.builder()
          .member(rq.getMember())
          .product(orderItem.getProduct())
          .build();
      myBookRepository.save(myBook);
    }
  }

  public List<MyBookDto> findList() {
    List<MyBook> myBookList = myBookRepository.findAllByMemberId(rq.getMember().getId());
    return myBookList.stream().map(myBook -> MyBookDto.from(myBook.getProduct()))
        .collect(Collectors.toList());
  }
}
