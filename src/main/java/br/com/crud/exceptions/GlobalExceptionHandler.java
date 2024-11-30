package br.com.crud.exceptions;

import br.com.crud.exceptions.models.ApiError;
import br.com.crud.exceptions.models.FieldError;
import br.com.crud.exceptions.models.ValidationApiError;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({ ServiceException.class, HttpMessageNotReadableException.class })
  public ResponseEntity<ApiError> handleDefaultException(HttpServletRequest req, RuntimeException ex) {
    String message = ex.getMessage();
    HttpStatus status = HttpStatus.BAD_REQUEST;
    String errorMessage = "Não foi possível completar a solicitação.";
    String path = req.getRequestURI();

    ApiError error = new ApiError(errorMessage, status.value(), message, path);

    return new ResponseEntity<>(error, status);
  }

  @ExceptionHandler(value = DataIntegrityViolationException.class)
  public ResponseEntity<ApiError> handleDataIntegrityViolationException(HttpServletRequest req, DataIntegrityViolationException ex) {
    String message = recoverViolationMessage(req, ex);
    HttpStatus status = HttpStatus.BAD_REQUEST;
    String errorMessage = "Ação necessária para completar a solicitação.";
    String path = req.getRequestURI();

    ApiError error = new ApiError(errorMessage, status.value(), message, path);

    return new ResponseEntity<>(error, status);
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationApiError> handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    String errorMessage = "A solicitação possui campos com formatos inválidos.";
    String path = req.getRequestURI();

    List<FieldError> invalidFields = ex.getBindingResult()
      .getFieldErrors()
      .stream()
      .map(errorField -> new FieldError(errorField.getField(), errorField.getDefaultMessage()))
      .toList();

    ValidationApiError error = new ValidationApiError(errorMessage, status.value(), path, invalidFields);

    return new ResponseEntity<>(error, status);
  }

  private String recoverViolationMessage(HttpServletRequest req, DataIntegrityViolationException ex) {
    if (ex.getMessage().contains("violates foreign key")) {
      String modelo = req.getRequestURI().contains("clientes") ? "cliente" : "produto";
      return "Não foi possível apagar o %s informado pois existem pedidos vínculados a ele.".formatted(modelo);
    }

    return ex.getMessage();
  }

}