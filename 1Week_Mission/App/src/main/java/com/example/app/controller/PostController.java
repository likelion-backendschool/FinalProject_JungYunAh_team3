package com.example.app.controller;

import com.example.app.domain.Member;
import com.example.app.domain.Post;
import com.example.app.dto.request.PostDto;
import com.example.app.service.PostService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

  private final PostService postService;

  /*
  글 등록
   */
  @GetMapping("/write")
  public String write() {
    return "post/write";
  }

  @PostMapping("/write")
  public String write(@Valid PostDto postDto, BindingResult bindingResult,
      @AuthenticationPrincipal Member member) {

    if (bindingResult.hasErrors()) {
      return "post/write";
    }

    Post post = postService.write(postDto, member);
    return "redirect:/post/%d".formatted(post.getId());
  }

  /*
  글 상세
   */
  @GetMapping("/{id}")
  public String detail(@PathVariable Long id) {
    return "post/postDetail";
  }
}
