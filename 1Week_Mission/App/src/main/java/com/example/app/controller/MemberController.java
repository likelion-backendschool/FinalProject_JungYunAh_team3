package com.example.app.controller;

import com.example.app.domain.Member;
import com.example.app.dto.request.JoinDto;
import com.example.app.dto.request.MemberModifyDto;
import com.example.app.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

  /*
  로그인
   */
  @GetMapping("/login")
  public String login(){
    return "member/login";
  }

  /*
  프로필
   */
  @GetMapping("/modify")
  public String modify() {
    return "member/modify";
  }

  /*
  회원 정보 수정
   */
  @PostMapping("/modify")
  public String modify(@Valid MemberModifyDto memberModifyDto, BindingResult bindingResult,
      @AuthenticationPrincipal Member member) {

    if (bindingResult.hasErrors()) {
      return "member/modify";
    }

    memberService.modify(memberModifyDto, member);
    return null;
  }
}
