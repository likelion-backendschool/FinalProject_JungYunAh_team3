package com.ll.exam.final__2022_10_08.app.myBook.repository;

import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.myBook.dto.MyBookDto;
import java.util.List;

public interface MyBookRepositoryCustom {

  List<MyBookDto> findMyBook(Member member);
}
