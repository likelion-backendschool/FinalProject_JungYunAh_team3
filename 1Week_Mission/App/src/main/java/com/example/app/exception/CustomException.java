package com.example.app.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

  private final String errorCode;
  private final String message;

  public CustomException(ErrorType errorType) {
    this.errorCode = errorType.getErrorCode();
    this.message = errorType.getMessage();
  }

  @Override
  public String toString() {
    return "%s %s".formatted(errorCode, message);
  }
}
