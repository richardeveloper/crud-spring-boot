package br.com.crud.services;

import br.com.crud.entities.PedidoEntity;
import br.com.crud.models.requests.PedidoResquest;

import java.util.List;

public interface PedidoService {

  PedidoEntity cadastrarPedido(PedidoResquest resquest);

  PedidoEntity buscarPedido(Long id);

  List<PedidoEntity> buscarPedidos();

  List<PedidoEntity> buscarPedidosPorCliente(Long clienteId);

  PedidoEntity editarPedido(Long id, PedidoResquest resquest);

  void apagarPedido(Long id);
}