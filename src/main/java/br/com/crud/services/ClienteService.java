package br.com.crud.services;

import br.com.crud.entities.ClienteEntity;
import br.com.crud.models.requests.ClienteRequest;

import java.util.List;

public interface ClienteService {

  ClienteEntity cadastrarCliente(ClienteRequest resquest);

  ClienteEntity buscarCliente(Long id);

  List<ClienteEntity> buscarClientesPorNome(String nome);

  List<ClienteEntity> buscarTodosClientes();

  ClienteEntity editarCliente(Long id, ClienteRequest resquest);

  void apagarCliente(Long id);

}