package br.com.crud.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo do pedido")
public class PedidoRequest {

  @Schema(description = "Id do cliente", example = "1")
  @NotNull(message = "O campo clienteId deve ser preenchido.")
  private Long clienteId;

  @Schema(description = "Lista de id dos produtos", example = "[1]")
  @NotNull(message = "O campo produtoIds deve ser preenchido.")
  private List<Long> produtoIds;

}
