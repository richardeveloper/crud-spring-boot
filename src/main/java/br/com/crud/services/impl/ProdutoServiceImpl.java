package br.com.crud.services.impl;

import br.com.crud.entities.ProdutoEntity;
import br.com.crud.exceptions.ServiceException;
import br.com.crud.repositories.ProdutoRepository;
import br.com.crud.requests.ProdutoResquest;
import br.com.crud.services.ProdutoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoServiceImpl implements ProdutoService {

  @Autowired
  private ProdutoRepository produtoRepository;

  @Override
  public ProdutoEntity cadastrarProduto(ProdutoResquest resquest) {
    ProdutoEntity produtoEntity = new ProdutoEntity();
    produtoEntity.setNome(resquest.getNome());
    produtoEntity.setPreco(resquest.getPreco());

    return produtoRepository.save(produtoEntity);
  }

  @Override
  public ProdutoEntity buscarProduto(Long id) {
    return buscarProdutoPorID(id);
  }

  @Override
  public ProdutoEntity buscarProdutoPorNome(String nome) {
    return produtoRepository.findByNome(nome)
      .orElseThrow(() -> new ServiceException(String.format("Não foi encontrado produto com nome parecido a '%s'.", nome)));
  }

  @Override
  public List<ProdutoEntity> buscarProdutos() {
    return produtoRepository.findAll();
  }

  @Override
  public ProdutoEntity editarProduto(Long id, ProdutoResquest resquest) {
    ProdutoEntity produtoEntity = buscarProdutoPorID(id);

    if (resquest.getNome() != null) {
      produtoEntity.setNome(resquest.getNome());
    }

    if (resquest.getPreco() != null) {
      produtoEntity.setPreco(resquest.getPreco());
    }

    return produtoRepository.save(produtoEntity);
  }

  @Override
  public void apagarProduto(Long id) {
    ProdutoEntity produtoEntity = buscarProdutoPorID(id);

    produtoRepository.delete(produtoEntity);
  }

  private ProdutoEntity buscarProdutoPorID(Long id) {
    return produtoRepository.findById(id)
      .orElseThrow(() -> new ServiceException("Não foi encontrado produto para o id informado."));
  }

}
