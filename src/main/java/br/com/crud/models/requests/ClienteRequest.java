package br.com.crud.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo do cliente")
public class ClienteRequest {

  @Schema(description = "Nome do cliente", example = "Franz Kafka")
  @NotBlank(message = "O campo nome deve ser preenchido.")
  private String nome;

  @Schema(description = "E-mail do cliente", example = "franz.kafka@email.com")
  @NotBlank(message = "O campo email deve ser preenchido.")
  @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$", message = "O campo e-mail deve seguir o padrão [exemplo@exemplo.com].")
  private String email;

  @Schema(description = "Telefone do cliente", example = "99999999999")
  @NotBlank(message = "O campo telefone deve ser preenchido.")
  @Length(min = 11, max = 11, message = "O campo telefone deve ter 11 dígitos.")
  @Pattern(regexp = "^[0-9]+$", message = "O campo telefone deve ter apenas números.")
  private String telefone;

}
