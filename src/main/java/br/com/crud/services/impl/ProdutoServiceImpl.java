package br.com.crud.services.impl;

import br.com.crud.entities.ProdutoEntity;
import br.com.crud.exceptions.ServiceException;
import br.com.crud.repositories.ProdutoRepository;
import br.com.crud.models.requests.ProdutoRequest;
import br.com.crud.services.ProdutoService;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProdutoServiceImpl implements ProdutoService {

  private final ProdutoRepository produtoRepository;

  public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
    this.produtoRepository = produtoRepository;
  }

  @Override
  public ProdutoEntity cadastrarProduto(ProdutoRequest resquest) {
    validarCampos(resquest);

    ProdutoEntity produtoEntity = new ProdutoEntity();
    produtoEntity.setNome(resquest.getNome());
    produtoEntity.setPreco(resquest.getPreco());
    produtoEntity.setUrlImagem(resquest.getUrlImagem());

    return produtoRepository.save(produtoEntity);
  }

  @Override
  public ProdutoEntity buscarProduto(Long id) {
    return buscarProdutoPorId(id);
  }

  @Override
  public List<ProdutoEntity> buscarProdutosPorNome(String nome) {
    return produtoRepository.findAllByNome(nome);
  }

  @Override
  public List<ProdutoEntity> buscarProdutos() {
    return produtoRepository.findAllOrderById();
  }

  @Override
  public ProdutoEntity editarProduto(Long id, ProdutoRequest resquest) {
    ProdutoEntity produtoEntity = buscarProdutoPorId(id);

    validarCampos(resquest);

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

  private void validarCampos(ProdutoRequest resquest) {
    if (produtoRepository.existsByNome(resquest.getNome())) {
      throw new ServiceException("O nome %s já está sendo utilizado.".formatted(resquest.getNome()));
    }
  }

}
