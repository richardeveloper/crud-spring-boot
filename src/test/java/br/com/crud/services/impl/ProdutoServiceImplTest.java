package br.com.crud.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.crud.entities.ProdutoEntity;
import br.com.crud.exceptions.ServiceException;
import br.com.crud.repositories.ProdutoRepository;
import br.com.crud.requests.ProdutoResquest;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ProdutoServiceImplTest {

  @Mock
  private ProdutoRepository produtoRepository;

  @InjectMocks
  private ProdutoServiceImpl produtoService;

  private Long id;
  private String nome;
  private ProdutoEntity entity;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    id = 1L;
    nome = "Produto";
    
    entity = new ProdutoEntity();
    entity.setId(id);
    entity.setNome(nome);
    entity.setPreco(new BigDecimal("100.00"));
  }

  @Test
  public void cadastrarProduto_deveCadastrarProdutoComSucesso() {
    ProdutoResquest resquest = new ProdutoResquest();
    resquest.setNome("Produto");
    resquest.setPreco(new BigDecimal("100.00"));

    when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(entity);

    ProdutoEntity produtoEntity = produtoService.cadastrarProduto(resquest);

    assertEquals("Produto", produtoEntity.getNome());
    assertEquals(new BigDecimal("100.00"), produtoEntity.getPreco());
  }

  @Test
  public void buscarProduto_deveBuscarProdutoPorIdComSucesso() {
    when(produtoRepository.findById(id)).thenReturn(Optional.of(entity));

    ProdutoEntity produtoEntity = produtoService.buscarProduto(id);

    assertEquals("Produto", produtoEntity.getNome());
    assertEquals(new BigDecimal("100.00"), produtoEntity.getPreco());
  }

  @Test
  public void buscarProduto_deveLancarExcecaoQuandoProdutoNaoEncontrado() {
    when(produtoRepository.findById(id)).thenReturn(Optional.empty());

    ServiceException exception = assertThrows(ServiceException.class,
      () -> produtoService.buscarProduto(id));

    String message = "Não foi encontrado produto para o id informado.";

    assertEquals(exception.getMessage(), message);
  }

  @Test
  public void buscarProdutoPorNome_deveBuscarProdutoPorNomeComSucesso() {
    when(produtoRepository.findByNome(nome)).thenReturn(Optional.of(entity));

    ProdutoEntity produtoEntity = produtoService.buscarProdutoPorNome(nome);

    assertEquals("Produto", produtoEntity.getNome());
    assertEquals(new BigDecimal("100.00"), produtoEntity.getPreco());
  }

  @Test
  public void buscarProdutoPorNome_deveLancarExcecaoQuandoProdutoNaoEncontrado() {
    when(produtoRepository.findByNome(nome)).thenReturn(Optional.empty());

    ServiceException exception = assertThrows(ServiceException.class,
      () -> produtoService.buscarProdutoPorNome(nome));

    String message = String.format("Não foi encontrado produto com nome parecido a '%s'.", nome);

    assertEquals(exception.getMessage(), message);
  }

  @Test
  public void buscarProdutos_deveBuscarTodosProdutosComSucesso() {
    ProdutoEntity entity2 = new ProdutoEntity();
    entity2.setId(2L);
    entity2.setNome("Produto II");
    entity2.setPreco(new BigDecimal("50.00"));

    List<ProdutoEntity> entitites = List.of(entity, entity2);

    when(produtoRepository.findAll()).thenReturn(entitites);

    List<ProdutoEntity> produtos = produtoService.buscarProdutos();

    assertNotNull(produtos);
    assertFalse(produtos.isEmpty());

    assertEquals("Produto", produtos.get(0).getNome());
    assertEquals(new BigDecimal("100.00"), produtos.get(0).getPreco());

    assertEquals("Produto II", produtos.get(1).getNome());
    assertEquals(new BigDecimal("50.00"), produtos.get(1).getPreco());
  }

  @Test
  public void editarProduto_deveEditarTodosCamposComSucesso() {
    ProdutoResquest resquest = new ProdutoResquest();
    resquest.setNome("Produto Atualizacao");
    resquest.setPreco(new BigDecimal("50.00"));

    when(produtoRepository.findById(id)).thenReturn(Optional.of(entity));

    entity.setNome(resquest.getNome());
    entity.setPreco(resquest.getPreco());

    when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(entity);

    ProdutoEntity produto = produtoService.editarProduto(id, resquest);

    assertEquals("Produto Atualizacao", produto.getNome());
    assertEquals(new BigDecimal("50.00"), produto.getPreco());
  }

  @Test
  public void editarProduto_deveEditarCampoNomeComSucesso() {
    ProdutoResquest resquest = new ProdutoResquest();
    resquest.setNome("Produto Atualizacao");

    when(produtoRepository.findById(id)).thenReturn(Optional.of(entity));

    entity.setNome(resquest.getNome());

    when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(entity);

    ProdutoEntity produto = produtoService.editarProduto(id, resquest);

    assertEquals("Produto Atualizacao", produto.getNome());
  }

  @Test
  public void editarProduto_deveEditarCampoPrecoComSucesso() {
    ProdutoResquest resquest = new ProdutoResquest();
    resquest.setPreco(new BigDecimal("25.00"));

    when(produtoRepository.findById(id)).thenReturn(Optional.of(entity));

    entity.setPreco(resquest.getPreco());

    when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(entity);

    ProdutoEntity produto = produtoService.editarProduto(id, resquest);

    assertEquals(new BigDecimal("25.00"), produto.getPreco());
  }

  @Test
  public void editarProduto_deveLancarExcecaoQuandoProdutoNaoEncontrado() {
    when(produtoRepository.findById(id)).thenReturn(Optional.empty());

    ServiceException exception = assertThrows(ServiceException.class,
      () -> produtoService.editarProduto(id, new ProdutoResquest()));

    String message = "Não foi encontrado produto para o id informado.";

    assertEquals(exception.getMessage(), message);
  }

  @Test
  public void apagarProduto_deveApagarClinteComSucesso() {
    when(produtoRepository.findById(id)).thenReturn(Optional.ofNullable(entity));

    produtoService.apagarProduto(id);

    verify(produtoRepository, times(1)).delete(entity);
  }

}