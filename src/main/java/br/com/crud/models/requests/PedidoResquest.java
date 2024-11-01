package br.com.crud.models.requests;

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
public class PedidoResquest {

  @NotNull(message = "O campo clienteId deve ser preenchido.")
  private Long clienteId;

  @NotNull(message = "O campo produtoIds deve ser preenchido.")
  private List<Long> produtoIds;

}
