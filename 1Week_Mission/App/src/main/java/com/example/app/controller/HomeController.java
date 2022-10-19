package com.example.app.controller;

import com.example.app.domain.Post;
import com.example.app.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

  private final PostService postService;

  @GetMapping("/")
  public String home(Model model) {
    List<Post> postList = postService.getList();
    model.addAttribute("postList", postList);
    return "index.html";
  }
}
