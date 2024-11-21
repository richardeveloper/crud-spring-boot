package br.com.crud.models.responses;

import br.com.crud.entities.PedidoEntity;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
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

  private BigDecimal valorTotal;

  private String dataPedido;

  public PedidoResponse(PedidoEntity pedidoEntity) {
    this.id = pedidoEntity.getId();
    this.cliente = new ClienteResponse(pedidoEntity.getCliente());
    this.produtos = pedidoEntity.getProdutos().stream().map(ProdutoResponse::new).toList();
    this.valorTotal = pedidoEntity.getValorTotal();
    this.dataPedido = pedidoEntity.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
  }

}
