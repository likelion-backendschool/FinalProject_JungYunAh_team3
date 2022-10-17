package com.example.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {

  DUPLICATED("409", "중복되어 사용할 수 없습니다."),
  MISMATCH("400", "비밀번호가 일치하지 않습니다.");

  private final String errorCode;
  private final String message;
}
