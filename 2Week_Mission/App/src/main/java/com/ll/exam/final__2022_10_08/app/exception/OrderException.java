package com.ll.exam.final__2022_10_08.app.exception;

public class OrderException extends CustomException {
  public OrderException(ErrorType errorType) {
    super(errorType);
  }
}