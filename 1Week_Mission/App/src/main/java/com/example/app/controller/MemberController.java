package com.example.app.controller;

import com.example.app.dto.request.JoinDto;
import com.example.app.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

  private final MemberService memberService;

  /*
  회원 가입
   */
  @GetMapping("/join")
  public String join() {
    return "member/join";
  }

  @PostMapping("/join")
  public String join(@Valid JoinDto joinDto, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return "member/join";
    }

    memberService.join(joinDto);
    return "redirect:/";
  }
}
