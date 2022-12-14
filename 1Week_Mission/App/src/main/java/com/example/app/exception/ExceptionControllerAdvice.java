package com.example.app.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler({MemberException.class, PostException.class})
  public String applicationHandler(CustomException e, Model model) {
    log.error(e.getMessage());
    model.addAttribute("ErrorCode", e.getErrorCode());
    model.addAttribute("message", e.getMessage());

    return "error/error";
  }
}
