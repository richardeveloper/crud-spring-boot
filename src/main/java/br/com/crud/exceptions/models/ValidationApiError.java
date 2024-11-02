package br.com.crud.exceptions.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ValidationApiError {

  private String error;

  private Integer status;

  private String path;

  private String timestamp;

  private List<FieldError> invalidFields;

  public ValidationApiError(String error, Integer status, String path, List<FieldError> invalidFields) {
    this.error = error;
    this.status = status;
    this.invalidFields = invalidFields;
    this.path = path;
    this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
  }
}
