package com.raktkosh.exceptions;

public class ActivationError extends RuntimeException {

  private static final long serialVersionUID = 5L;
  
  public ActivationError(String message) {
    super(message);
  }

}
