package com.ll.exam.final__2022_10_08.app.exception;

import com.ll.exam.final__2022_10_08.app.exception.CustomException;
import com.ll.exam.final__2022_10_08.app.exception.ErrorType;

public class CartException extends CustomException {
  public CartException(ErrorType errorType) {
    super(errorType);
  }
}
