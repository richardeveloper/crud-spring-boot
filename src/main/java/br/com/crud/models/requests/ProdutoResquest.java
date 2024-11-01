package br.com.crud.models.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResquest {

  @NotBlank(message = "O campo nome deve ser preenchido.")
  private String nome;

  @NotNull(message = "O campo preço deve ser preenchido.")
  @Min(value = 0, message = "O campo preço não pode ser negativo.")
  private BigDecimal preco;

}
