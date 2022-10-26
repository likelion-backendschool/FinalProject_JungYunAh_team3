package com.ll.exam.final__2022_10_08.app.myBook.repository;

import com.ll.exam.final__2022_10_08.app.myBook.entity.MyBook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyBookRepository extends JpaRepository<MyBook, Long> {

  List<MyBook> findAllByMemberId(Long id);
}
