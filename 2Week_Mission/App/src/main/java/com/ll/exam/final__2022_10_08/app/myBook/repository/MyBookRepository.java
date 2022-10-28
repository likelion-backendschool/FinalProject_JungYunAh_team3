package com.ll.exam.final__2022_10_08.app.myBook.repository;

import com.ll.exam.final__2022_10_08.app.myBook.entity.MyBook;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyBookRepository extends JpaRepository<MyBook, Long> {

  List<MyBook> findAllByMemberId(Long id);

  Optional<MyBook> findByProductId(Long id);
}
