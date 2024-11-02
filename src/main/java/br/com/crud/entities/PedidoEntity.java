package br.com.crud.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PEDIDOS")
public class PedidoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "CLIENTE_ID")
  private ClienteEntity cliente;

  @ManyToMany
  @JoinTable(
    name = "PEDIDOS_PRODUTOS",
    joinColumns = @JoinColumn(name = "PEDIDO_ID"),
    inverseJoinColumns = @JoinColumn(name = "PRODUTO_ID")
  )
  private List<ProdutoEntity> produtos;

  @Column(name = "VALOR_TOTAL")
  private BigDecimal valorTotal;

  public void calcularValorTotal() {
    this.valorTotal = this.produtos.stream()
      .map(ProdutoEntity::getPreco)
      .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
