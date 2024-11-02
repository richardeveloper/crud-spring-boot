package br.com.crud.services.impl;

import br.com.crud.entities.ClienteEntity;
import br.com.crud.entities.PedidoEntity;
import br.com.crud.entities.ProdutoEntity;
import br.com.crud.exceptions.ServiceException;
import br.com.crud.repositories.ClienteRepository;
import br.com.crud.repositories.PedidoRepository;
import br.com.crud.repositories.ProdutoRepository;
import br.com.crud.models.requests.PedidoResquest;
import br.com.crud.services.PedidoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

  @Autowired
  private PedidoRepository pedidoRepository;

  @Autowired
  private ClienteRepository clienteRepository;

  @Autowired
  private ProdutoRepository produtoRepository;

  @Override
  public PedidoEntity cadastrarPedido(PedidoResquest resquest) {
    if (resquest.getProdutoIds().isEmpty()) {
      throw new ServiceException("É obrigatório informar os produtos do pedido.");
    }

    ClienteEntity clienteEntity = buscarClientePorId(resquest.getClienteId());

    List<ProdutoEntity> produtoEntities = recuperarProdutos(resquest);

    PedidoEntity pedidoEntity = new PedidoEntity();
    pedidoEntity.setClienteEntity(clienteEntity);
    pedidoEntity.setProdutoEntities(produtoEntities);

    return pedidoRepository.save(pedidoEntity);
  }

  @Override
  public PedidoEntity buscarPedido(Long id) {
    return buscarPedidoPorId(id);
  }

  @Override
  public List<PedidoEntity> buscarPedidos() {
    return pedidoRepository.findAll();
  }

  @Override
  public List<PedidoEntity> buscarPedidosPorCliente(Long clienteId) {
    ClienteEntity clienteEntity = buscarClientePorId(clienteId);

    return pedidoRepository.findByClienteEntity(clienteEntity);
  }

  @Override
  public PedidoEntity editarPedido(Long id, PedidoResquest resquest) {
    PedidoEntity pedidoEntity = buscarPedidoPorId(id);

    buscarClientePorId(resquest.getClienteId());

    List<ProdutoEntity> produtoEntities = recuperarProdutos(resquest);

    pedidoEntity.setProdutoEntities(produtoEntities);

    return pedidoRepository.save(pedidoEntity);
  }

  @Override
  public void apagarPedido(Long id) {
    PedidoEntity PedidoEntity = buscarPedidoPorId(id);

    pedidoRepository.delete(PedidoEntity);
  }

  private PedidoEntity buscarPedidoPorId(Long id) {
    return pedidoRepository.findById(id)
      .orElseThrow(() -> new ServiceException("Não foi encontrado pedido para o id informado."));
  }

  private ClienteEntity buscarClientePorId(Long id) {
    return clienteRepository.findById(id)
      .orElseThrow(() -> new ServiceException("Não foi encontrado cliente para o id informado."));
  }

  private List<ProdutoEntity> recuperarProdutos(PedidoResquest resquest) {
    List<ProdutoEntity> produtoEntities = new ArrayList<>();
    List<Long> produtosNaoEncontrados = new ArrayList<>();

    for (Long produtoId : resquest.getProdutoIds()) {
      produtoRepository.findById(produtoId)
        .ifPresentOrElse(
          produtoEntities::add,
          () -> produtosNaoEncontrados.add(produtoId)
        );
    }

    if (!produtosNaoEncontrados.isEmpty()) {
      String produtos = Arrays.toString(produtosNaoEncontrados.toArray());
      throw new ServiceException("Os produtos de id %s não foram identificados.".formatted(produtos));
    }

    return produtoEntities;
  }

}
