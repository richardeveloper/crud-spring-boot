package br.com.crud.exceptions.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiError {

  private String error;

  private Integer status;

  private String message;

  private String path;

  private String timestamp;

  public ApiError(String error, Integer status, String message, String path) {
    this.error = error;
    this.status = status;
    this.message = message;
    this.path = path;
    this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
  }
}
