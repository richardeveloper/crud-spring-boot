package br.com.crud.models.responses;

import br.com.crud.entities.PedidoEntity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponse {

  private Long id;

  private ClienteResponse cliente;

  private List<ProdutoResponse> produtos;

  public PedidoResponse(PedidoEntity pedidoEntity) {
    this.id = pedidoEntity.getId();
    this.cliente = new ClienteResponse(pedidoEntity.getClienteEntity());
    this.produtos = pedidoEntity.getProdutoEntities().stream().map(ProdutoResponse::new).toList();
  }

}
