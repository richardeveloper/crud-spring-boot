package br.com.crud.exceptions;

public class ServiceException extends RuntimeException {

  public ServiceException(String message) {
    super(message);
  }
}
