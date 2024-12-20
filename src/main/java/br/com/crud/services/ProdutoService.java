package br.com.crud.services;

import br.com.crud.entities.ProdutoEntity;
import br.com.crud.models.requests.ProdutoRequest;

import java.util.List;

public interface ProdutoService {

  ProdutoEntity cadastrarProduto(ProdutoRequest resquest);

  ProdutoEntity buscarProduto(Long id);

  List<ProdutoEntity> buscarProdutosPorNome(String nome);

  List<ProdutoEntity> buscarProdutos();

  ProdutoEntity editarProduto(Long id, ProdutoRequest resquest);

  void apagarProduto(Long id);

}