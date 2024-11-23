package br.com.crud.services;

import br.com.crud.entities.PedidoEntity;
import br.com.crud.models.requests.PedidoRequest;

import java.util.List;

public interface PedidoService {

  PedidoEntity cadastrarPedido(PedidoRequest resquest);

  PedidoEntity buscarPedido(Long id);

  List<PedidoEntity> buscarTodosPedidos();

  List<PedidoEntity> buscarPedidosPorCliente(Long clienteId);

  PedidoEntity editarPedido(Long id, PedidoRequest resquest);

  void apagarPedido(Long id);
}