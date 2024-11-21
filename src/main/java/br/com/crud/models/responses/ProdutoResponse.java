package br.com.crud.models.responses;

import br.com.crud.entities.ProdutoEntity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponse {

  private Long id;

  private String nome;

  private BigDecimal preco;

  private String urlImagem;

  public ProdutoResponse(ProdutoEntity produtoEntity) {
    this.id = produtoEntity.getId();
    this.nome = produtoEntity.getNome();
    this.preco = produtoEntity.getPreco();
    this.urlImagem = produtoEntity.getUrlImagem();
  }

}
