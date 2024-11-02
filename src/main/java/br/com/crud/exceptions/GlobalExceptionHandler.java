package br.com.crud.exceptions;

import br.com.crud.exceptions.models.ApiError;
import br.com.crud.exceptions.models.FieldError;
import br.com.crud.exceptions.models.ValidationApiError;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = ServiceException.class)
  public ResponseEntity<ApiError> handleServiceException(HttpServletRequest req, ServiceException ex) {
    String message = ex.getMessage();
    HttpStatus status = HttpStatus.BAD_REQUEST;
    String error = "Erro no processamento da solicitação.";
    String path = req.getRequestURI();

    ApiError apiError = new ApiError(error, status.value(), message, path);

    return new ResponseEntity<>(apiError, status);
  }

  @ExceptionHandler(value = DataIntegrityViolationException.class)
  public ResponseEntity<ApiError> handleDataIntegrityViolationException(HttpServletRequest req, DataIntegrityViolationException ex) {
    String message = recoverViolationMessage(req, ex);
    HttpStatus status = HttpStatus.BAD_REQUEST;
    String error = "Erro no processamento da solicitação.";
    String path = req.getRequestURI();

    ApiError apiError = new ApiError(error, status.value(), message, path);

    return new ResponseEntity<>(apiError, status);
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationApiError> handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    String error = "Existem campos com formato inválido.";
    String path = req.getRequestURI();

    List<FieldError> invalidFields = ex.getBindingResult()
      .getFieldErrors()
      .stream()
      .map(errorField -> new FieldError(errorField.getField(), errorField.getDefaultMessage()))
      .toList();

    ValidationApiError apiError = new ValidationApiError(error, status.value(), path, invalidFields);

    return new ResponseEntity<>(apiError, status);
  }

  private String recoverViolationMessage(HttpServletRequest req, DataIntegrityViolationException ex) {
    if (ex.getMessage().contains("violates foreign key")) {
      String modelo = req.getRequestURI().contains("clientes") ? "cliente" : "produto";
      return "Não é possível apagar o %s informado pois existem pedidos vínculados a ele.".formatted(modelo);
    }

    return ex.getMessage();
  }

}