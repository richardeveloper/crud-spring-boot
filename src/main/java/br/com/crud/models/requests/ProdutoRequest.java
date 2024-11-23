package br.com.crud.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(description = "Modelo do produto")
public class ProdutoRequest {

  @Schema(description = "Nome do produto", example = "Apple Watch SE")
  @NotBlank(message = "O campo nome deve ser preenchido.")
  private String nome;

  @Schema(description = "Preço do produto", example = "1899.99")
  @NotNull(message = "O campo preço deve ser preenchido.")
  @Min(value = 0, message = "O campo preço não pode ser negativo.")
  private BigDecimal preco;

  @Schema(description = "URL da imagem")
  @NotNull(message = "O campo urlImagem deve ser preenchido.")
  private String urlImagem;

}
