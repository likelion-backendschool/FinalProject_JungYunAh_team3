package com.example.app.exception;

public class PostException extends CustomException{

  public PostException(ErrorType errorType) {
    super(errorType);
  }
}
