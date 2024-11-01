package br.com.crud.services.impl;

import br.com.crud.entities.ClienteEntity;
import br.com.crud.exceptions.ServiceException;
import br.com.crud.repositories.ClienteRepository;
import br.com.crud.models.requests.ClienteResquest;
import br.com.crud.services.ClienteService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

  @Autowired
  private ClienteRepository clienteRepository;

  @Override
  public ClienteEntity cadastrarCliente(ClienteResquest resquest) {
    ClienteEntity clienteEntity = new ClienteEntity();
    clienteEntity.setNome(resquest.getNome());
    clienteEntity.setEmail(resquest.getEmail());
    clienteEntity.setTelefone(resquest.getTelefone());

    return clienteRepository.save(clienteEntity);
  }

  @Override
  public ClienteEntity buscarCliente(Long id) {
    return buscarClientePorID(id);
  }

  @Override
  public ClienteEntity buscarClientePorNome(String nome) {
    return clienteRepository.findByNome(nome)
      .orElseThrow(() -> new ServiceException(String.format("Não foi encontrado cliente com nome parecido a '%s'.", nome)));
  }

  @Override
  public List<ClienteEntity> buscarClientes() {
    return clienteRepository.findAll();
  }

  @Override
  public ClienteEntity editarCliente(Long id, ClienteResquest resquest) {
    ClienteEntity clienteEntity = buscarClientePorID(id);

    if (resquest.getNome() != null) {
      clienteEntity.setNome(resquest.getNome());
    }

    if (resquest.getEmail() != null) {
      clienteEntity.setEmail(resquest.getEmail());
    }

    if (resquest.getTelefone() != null) {
      clienteEntity.setTelefone(resquest.getTelefone());
    }

    return clienteRepository.save(clienteEntity);
  }

  @Override
  public void apagarCliente(Long id) {
    ClienteEntity ClienteEntity = buscarClientePorID(id);

    clienteRepository.delete(ClienteEntity);
  }

  private ClienteEntity buscarClientePorID(Long id) {
    return clienteRepository.findById(id)
      .orElseThrow(() -> new ServiceException("Não foi encontrado cliente para o id informado."));
  }

}
