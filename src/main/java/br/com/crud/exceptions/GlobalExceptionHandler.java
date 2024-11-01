package br.com.crud.exceptions;

import br.com.crud.exceptions.models.ApiError;
import br.com.crud.exceptions.models.FieldError;
import br.com.crud.exceptions.models.ValidationApiError;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

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

    ApiError apiError = new ApiError(error, status.value(), message, path, LocalDateTime.now());

    return new ResponseEntity<>(apiError, status);
  }

  @ExceptionHandler(value = DataIntegrityViolationException.class)
  public ResponseEntity<ApiError> handleDataIntegrityViolationException(HttpServletRequest req, DataIntegrityViolationException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    String error = "Erro no processamento da solicitação.";
    String path = req.getRequestURI();

    String modelo = path.contains("clientes") ? "cliente" : "produto";

    String message = ex.getMessage().contains("violates foreign key constraint")
      ? "Não foi possível apagar o " + modelo + " informado pois existem pedidos vínculados a ele."
      : ex.getMessage();

    ApiError apiError = new ApiError(error, status.value(), message, path, LocalDateTime.now());

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

    ValidationApiError apiError = new ValidationApiError(error, status.value(), path, LocalDateTime.now(), invalidFields);

    return new ResponseEntity<>(apiError, status);
  }

}