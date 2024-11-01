package br.com.crud.models.requests;

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
public class ClienteResquest {

  @NotBlank(message = "O campo nome deve ser preenchido.")
  private String nome;

  @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$", message = "O campo e-mail deve seguir o padrão [exemplo@exemplo.com].")
  @NotBlank(message = "O campo email deve ser preenchido.")
  private String email;

  @Length(min = 11, max = 11, message = "O campo telefone deve ter 11 dígitos")
  @NotBlank(message = "O campo telefone deve ser preenchido.")
  private String telefone;

}
