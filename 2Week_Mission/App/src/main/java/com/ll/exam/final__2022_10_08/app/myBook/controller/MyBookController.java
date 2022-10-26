package com.ll.exam.final__2022_10_08.app.myBook.controller;

import com.ll.exam.final__2022_10_08.app.myBook.dto.MyBookDto;
import com.ll.exam.final__2022_10_08.app.myBook.service.MyBookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mybook")
public class MyBookController {

  private final MyBookService myBookService;

  @GetMapping("/list")
  @PreAuthorize("isAuthenticated()")
  public String showList(Model model) {
    List<MyBookDto> myBookList = myBookService.findList();
    model.addAttribute("myBookList", myBookList);
    return "myBook/list";
  }
}
