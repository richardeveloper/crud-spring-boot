package br.com.crud.services.impl;

import br.com.crud.entities.ProdutoEntity;
import br.com.crud.exceptions.ServiceException;
import br.com.crud.repositories.ProdutoRepository;
import br.com.crud.models.requests.ProdutoResquest;
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
    return buscarProdutoPorId(id);
  }

  @Override
  public ProdutoEntity buscarProdutoPorNome(String nome) {
    return produtoRepository.findByNome(nome)
      .orElseThrow(() -> new ServiceException("Não foi encontrado produto com nome parecido a '%s'.".formatted(nome)));
  }

  @Override
  public List<ProdutoEntity> buscarProdutos() {
    return produtoRepository.findAll();
  }

  @Override
  public ProdutoEntity editarProduto(Long id, ProdutoResquest resquest) {
    ProdutoEntity produtoEntity = buscarProdutoPorId(id);

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
    ProdutoEntity produtoEntity = buscarProdutoPorId(id);

    produtoRepository.delete(produtoEntity);
  }

  private ProdutoEntity buscarProdutoPorId(Long id) {
    return produtoRepository.findById(id)
      .orElseThrow(() -> new ServiceException("Não foi encontrado produto para o id informado."));
  }

}
