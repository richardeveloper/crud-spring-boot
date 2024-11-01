package br.com.crud.services;

import br.com.crud.entities.ClienteEntity;
import br.com.crud.requests.ClienteResquest;

import java.util.List;

public interface ClienteService {

  ClienteEntity cadastrarCliente(ClienteResquest resquest);

  ClienteEntity buscarCliente(Long id);

  ClienteEntity buscarClientePorNome(String nome);

  List<ClienteEntity> buscarClientes();

  ClienteEntity editarCliente(Long id, ClienteResquest resquest);

  void apagarCliente(Long id);

}