package com.ll.exam.final__2022_10_08.app.exception;

public class MyBookException extends CustomException {
  public MyBookException(ErrorType errorType) {
    super(errorType);
  }
}