package com.example.app.exception;

public class MemberException extends CustomException{

  public MemberException(ErrorType errorType) {
    super(errorType);
  }
}
