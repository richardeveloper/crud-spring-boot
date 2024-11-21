package br.com.crud.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.crud.entities.ClienteEntity;
import br.com.crud.entities.PedidoEntity;
import br.com.crud.entities.ProdutoEntity;
import br.com.crud.exceptions.ServiceException;
import br.com.crud.repositories.ClienteRepository;
import br.com.crud.repositories.PedidoRepository;
import br.com.crud.repositories.ProdutoRepository;
import br.com.crud.models.requests.PedidoResquest;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PedidoServiceImplTest {

  @Mock
  private PedidoRepository pedidoRepository;

  @Mock
  private ClienteRepository clienteRepository;

  @Mock
  private ProdutoRepository produtoRepository;

  @InjectMocks
  private PedidoServiceImpl pedidoService;

  private Long pedidoId;

  private ClienteEntity clienteEntity;
  private List<ProdutoEntity> produtoEntities;
  private PedidoEntity pedidoEntity;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    pedidoId = 1L;

    clienteEntity = new ClienteEntity();
    clienteEntity.setId(1L);
    clienteEntity.setNome("Cliente");
    clienteEntity.setEmail("teste@email.com");
    clienteEntity.setTelefone("99999999999");

    ProdutoEntity produtoEntity = new ProdutoEntity();
    produtoEntity.setId(1L);
    produtoEntity.setNome("Produto");
    produtoEntity.setPreco(new BigDecimal("12.99"));

    produtoEntities = new ArrayList<>();
    produtoEntities.add(produtoEntity);

    pedidoEntity = new PedidoEntity();
    pedidoEntity.setId(pedidoId);
    pedidoEntity.setCliente(clienteEntity);
    pedidoEntity.setProdutos(produtoEntities);
    pedidoEntity.calcularValorTotal();
  }

  @Test
  public void cadastrarPedido_deveLancarExcecaoQuandoListaProdutosVazia() {
    PedidoResquest resquest = new PedidoResquest();
    resquest.setClienteId(clienteEntity.getId());
    resquest.setProdutoIds(List.of());

    ServiceException exception = assertThrows(ServiceException.class, () -> pedidoService.cadastrarPedido(resquest));

    String message = "É obrigatório incluir ao menos 1 produto no pedido.";

    assertEquals(message, exception.getMessage());
  }

  @Test
  public void cadastrarPedido_deveCadastrarPedidoComSucesso() {
    PedidoResquest resquest = new PedidoResquest();
    resquest.setClienteId(1L);
    resquest.setProdutoIds(List.of(1L));

    when(clienteRepository.findById(pedidoId)).thenReturn(Optional.ofNullable(clienteEntity));
    when(produtoRepository.findById(pedidoId)).thenReturn(Optional.ofNullable(produtoEntities.get(0)));

    when(pedidoRepository.save(any())).thenReturn(pedidoEntity);

    PedidoEntity pedidoEntity = pedidoService.cadastrarPedido(resquest);

    assertNotNull(pedidoEntity.getCliente());
    assertEquals("Cliente", pedidoEntity.getCliente().getNome());
    assertEquals("teste@email.com", pedidoEntity.getCliente().getEmail());
    assertEquals("99999999999", pedidoEntity.getCliente().getTelefone());

    assertNotNull(pedidoEntity.getProdutos());
    assertFalse(pedidoEntity.getProdutos().isEmpty());
    assertEquals("Produto", pedidoEntity.getProdutos().get(0).getNome());
    assertEquals(new BigDecimal("12.99"), pedidoEntity.getProdutos().get(0).getPreco());

    assertEquals(new BigDecimal("12.99"), pedidoEntity.getValorTotal());
  }

  @Test
  public void cadastrarPedido_deveLancarExcecaoQuandoClienteNaoEncontrado() {
    PedidoResquest resquest = new PedidoResquest();
    resquest.setClienteId(clienteEntity.getId());
    resquest.setProdutoIds(produtoEntities.stream().map(ProdutoEntity::getId).toList());

    when(clienteRepository.findById(pedidoId)).thenReturn(Optional.empty());

    ServiceException exception = assertThrows(ServiceException.class,
      () -> pedidoService.cadastrarPedido(resquest));

    String message = "Não foi encontrado cliente para o id informado.";

    assertEquals(message, exception.getMessage());
  }

  @Test
  public void buscarPedido_deveBuscarPedidoPorIdComSucesso() {
    when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedidoEntity));

    PedidoEntity pedidoEntity = pedidoService.buscarPedido(pedidoId);

    assertNotNull(pedidoEntity.getCliente());
    assertEquals("Cliente", pedidoEntity.getCliente().getNome());
    assertEquals("teste@email.com", pedidoEntity.getCliente().getEmail());
    assertEquals("99999999999", pedidoEntity.getCliente().getTelefone());

    assertNotNull(pedidoEntity.getProdutos());
    assertFalse(pedidoEntity.getProdutos().isEmpty());
    assertEquals("Produto", pedidoEntity.getProdutos().get(0).getNome());
    assertEquals(new BigDecimal("12.99"), pedidoEntity.getProdutos().get(0).getPreco());

    assertEquals(new BigDecimal("12.99"), pedidoEntity.getValorTotal());
  }

  @Test
  public void buscarPedido_deveLancarExcecaoQuandoPedidoNaoEncontrado() {
    when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.empty());

    ServiceException exception = assertThrows(ServiceException.class, () -> pedidoService.buscarPedido(
      pedidoId));

    String message = "Não foi encontrado pedido para o id informado.";

    assertEquals(message, exception.getMessage());
  }

  @Test
  public void buscarPedidos_deveBuscarPedidosPorClienteIdComSucesso() {
    when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteEntity));
    when(pedidoRepository.findByCliente(clienteEntity)).thenReturn(List.of(pedidoEntity));

    List<PedidoEntity> pedidos = pedidoService.buscarPedidosPorCliente(1L);

    assertNotNull(pedidos);
    assertFalse(pedidos.isEmpty());

    assertEquals("Cliente", pedidos.get(0).getCliente().getNome());
    assertEquals("teste@email.com", pedidos.get(0).getCliente().getEmail());
    assertEquals("99999999999", pedidos.get(0).getCliente().getTelefone());

    assertEquals("Produto", pedidos.get(0).getProdutos().get(0).getNome());
    assertEquals(new BigDecimal("12.99"), pedidos.get(0).getProdutos().get(0).getPreco());

    assertEquals(new BigDecimal("12.99"), pedidoEntity.getValorTotal());
  }

  @Test
  public void buscarPedidos_deveBuscarTodosPedidosComSucesso() {
    ClienteEntity cliente = new ClienteEntity();
    cliente.setId(2L);
    cliente.setNome("Cliente II");
    cliente.setEmail("teste2@email.com");
    cliente.setTelefone("12345678900");

    ProdutoEntity produto = new ProdutoEntity();
    produto.setId(2L);
    produto.setNome("Produto II");
    produto.setPreco(new BigDecimal("24.99"));

    List<ProdutoEntity> produtos = List.of(produto);

    PedidoEntity pedido = new PedidoEntity();
    pedido.setId(2L);
    pedido.setCliente(cliente);
    pedido.setProdutos(produtos);
    pedido.calcularValorTotal();

    List<PedidoEntity> entitites = List.of(pedidoEntity, pedido);

    when(pedidoRepository.findAll()).thenReturn(entitites);

    List<PedidoEntity> pedidos = pedidoService.buscarTodosPedidos();

    assertNotNull(pedidos);
    assertFalse(pedidos.isEmpty());

    assertEquals("Cliente", pedidos.get(0).getCliente().getNome());
    assertEquals("teste@email.com", pedidos.get(0).getCliente().getEmail());
    assertEquals("99999999999", pedidos.get(0).getCliente().getTelefone());

    assertNotNull(pedidos.get(0).getProdutos());
    assertFalse(pedidos.get(0).getProdutos().isEmpty());
    assertEquals("Produto", pedidos.get(0).getProdutos().get(0).getNome());
    assertEquals(new BigDecimal("12.99"), pedidos.get(0).getProdutos().get(0).getPreco());

    assertEquals(new BigDecimal("12.99"), pedidos.get(0).getValorTotal());

    assertEquals("Cliente II", pedidos.get(1).getCliente().getNome());
    assertEquals("teste2@email.com", pedidos.get(1).getCliente().getEmail());
    assertEquals("12345678900", pedidos.get(1).getCliente().getTelefone());

    assertNotNull(pedidos.get(1).getProdutos());
    assertFalse(pedidos.get(1).getProdutos().isEmpty());
    assertEquals("Produto II", pedidos.get(1).getProdutos().get(0).getNome());
    assertEquals(new BigDecimal("24.99"), pedidos.get(1).getProdutos().get(0).getPreco());

    assertEquals(new BigDecimal("24.99"), pedidos.get(1).getValorTotal());
  }

  @Test
  public void editarPedido_deveEditarTodosCamposComSucesso() {
    PedidoResquest resquest = new PedidoResquest();
    resquest.setClienteId(1L);
    resquest.setProdutoIds(List.of(1L, 2L));

    ProdutoEntity produto2 = new ProdutoEntity();
    produto2.setId(2L);
    produto2.setNome("Produto II");
    produto2.setPreco(new BigDecimal("24.99"));

    when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoEntity));
    when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteEntity));
    when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoEntities.get(0)));
    when(produtoRepository.findById(2L)).thenReturn(Optional.of(produto2));

    pedidoEntity.getProdutos().add(produto2);

    when(pedidoRepository.save(any())).thenReturn(pedidoEntity);

    PedidoEntity pedidoEntity = pedidoService.editarPedido(pedidoId, resquest);

    assertNotNull(pedidoEntity.getCliente());
    assertEquals("Cliente", pedidoEntity.getCliente().getNome());
    assertEquals("teste@email.com", pedidoEntity.getCliente().getEmail());
    assertEquals("99999999999", pedidoEntity.getCliente().getTelefone());

    assertNotNull(pedidoEntity.getProdutos());
    assertFalse(pedidoEntity.getProdutos().isEmpty());

    assertEquals("Produto", pedidoEntity.getProdutos().get(0).getNome());
    assertEquals(new BigDecimal("12.99"), pedidoEntity.getProdutos().get(0).getPreco());

    assertEquals("Produto II", pedidoEntity.getProdutos().get(1).getNome());
    assertEquals(new BigDecimal("24.99"), pedidoEntity.getProdutos().get(1).getPreco());

    assertEquals(new BigDecimal("37.98"), pedidoEntity.getValorTotal());
  }

  @Test
  public void editarPedido_deveLancarExcecaoQuandoClienteNaoEncontrado() {
    when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoEntity));
    when(clienteRepository.findById(pedidoId)).thenReturn(Optional.empty());

    ServiceException exception = assertThrows(ServiceException.class,
      () -> pedidoService.editarPedido(pedidoId, new PedidoResquest()));

    String message = "Não foi encontrado cliente para o id informado.";

    assertEquals(message, exception.getMessage());
  }

  @Test
  public void editarPedido_deveLancarExcecaoQuandoProdutoNaoEncontrado() {
    PedidoResquest resquest = new PedidoResquest();
    resquest.setClienteId(1L);
    resquest.setProdutoIds(List.of(1L, 2L, 3L));

    when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoEntity));
    when(clienteRepository.findById(pedidoId)).thenReturn(Optional.of(clienteEntity));
    when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoEntities.get(0)));
    when(produtoRepository.findById(2L)).thenReturn(Optional.empty());
    when(produtoRepository.findById(3L)).thenReturn(Optional.empty());

    ServiceException exception = assertThrows(ServiceException.class,
      () -> pedidoService.editarPedido(pedidoId, resquest));

    String message = "Os produtos de id [2, 3] não foram identificados.";

    assertEquals(message, exception.getMessage());
  }

  @Test
  public void editarPedido_deveLancarExcecaoQuandoPedidoNaoEncontrado() {
    when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.empty());

    ServiceException exception = assertThrows(ServiceException.class,
      () -> pedidoService.editarPedido(pedidoId, new PedidoResquest()));

    String message = "Não foi encontrado pedido para o id informado.";

    assertEquals(message, exception.getMessage());
  }

  @Test
  public void apagarPedido_deveApagarClinteComSucesso() {
    when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.ofNullable(pedidoEntity));

    pedidoService.apagarPedido(pedidoId);

    verify(pedidoRepository, times(1)).delete(pedidoEntity);
  }

}