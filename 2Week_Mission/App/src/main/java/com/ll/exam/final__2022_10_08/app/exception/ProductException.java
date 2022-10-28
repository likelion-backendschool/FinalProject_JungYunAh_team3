package com.ll.exam.final__2022_10_08.app.exception;

public class ProductException  extends CustomException {
  public ProductException(ErrorType errorType) {
    super(errorType);
  }
}
