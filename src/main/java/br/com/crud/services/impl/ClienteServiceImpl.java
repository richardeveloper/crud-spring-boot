package br.com.crud.services.impl;

import br.com.crud.entities.ClienteEntity;
import br.com.crud.exceptions.ServiceException;
import br.com.crud.repositories.ClienteRepository;
import br.com.crud.models.requests.ClienteRequest;
import br.com.crud.services.ClienteService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

  private final ClienteRepository clienteRepository;

  public ClienteServiceImpl(ClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
  }

  @Override
  public ClienteEntity cadastrarCliente(ClienteRequest resquest) {
    validarCampos(resquest);

    ClienteEntity clienteEntity = new ClienteEntity();
    clienteEntity.setNome(resquest.getNome());
    clienteEntity.setEmail(resquest.getEmail());
    clienteEntity.setTelefone(resquest.getTelefone());
    clienteEntity.setDataCadastro(LocalDateTime.now());

    return clienteRepository.save(clienteEntity);
  }

  @Override
  public ClienteEntity buscarCliente(Long id) {
    return buscarClientePorId(id);
  }

  @Override
  public List<ClienteEntity> buscarClientesPorNome(String nome) {
    return clienteRepository.findAllByNome(nome);
  }

  @Override
  public List<ClienteEntity> buscarTodosClientes() {
    return clienteRepository.findAllOrderById();
  }

  @Override
  public ClienteEntity editarCliente(Long id, ClienteRequest request) {
    ClienteEntity clienteEntity = buscarClientePorId(id);

    if (!clienteEntity.getNome().equalsIgnoreCase(request.getNome())) {
      if (clienteRepository.existsByNome(request.getNome())) {
        throw new ServiceException("O nome %s já está sendo utilizado.".formatted(request.getNome()));
      }

      clienteEntity.setNome(request.getNome());
    }

    if (!clienteEntity.getEmail().equalsIgnoreCase(request.getEmail())) {
      if (clienteRepository.existsByEmail(request.getEmail())) {
        throw new ServiceException("O e-mail %s já está sendo utilizado.".formatted(request.getEmail()));
      }

      clienteEntity.setEmail(request.getEmail());
    }

    if (!clienteEntity.getTelefone().equalsIgnoreCase(request.getTelefone())) {
      if (clienteRepository.existsByTelefone(request.getTelefone())) {
        throw new ServiceException("O telefone %s já está sendo utilizado.".formatted(request.getTelefone()));
      }

      clienteEntity.setTelefone(request.getTelefone());
    }

    return clienteRepository.save(clienteEntity);
  }

  @Override
  public void apagarCliente(Long id) {
    ClienteEntity ClienteEntity = buscarClientePorId(id);

    clienteRepository.delete(ClienteEntity);
  }

  private ClienteEntity buscarClientePorId(Long id) {
    return clienteRepository.findById(id)
      .orElseThrow(() -> new ServiceException("Não foi encontrado cliente para o id informado."));
  }

  private void validarCampos(ClienteRequest request) {
    if (clienteRepository.existsByNome(request.getNome())) {
      throw new ServiceException("O nome %s já está sendo utilizado.".formatted(request.getNome()));
    }

    if (clienteRepository.existsByEmail(request.getEmail())) {
      throw new ServiceException("O e-mail %s já está sendo utilizado.".formatted(request.getEmail()));
    }

    if (clienteRepository.existsByTelefone(request.getTelefone())) {
      throw new ServiceException("O telefone %s já está sendo utilizado.".formatted(request.getTelefone()));
    }
  }

}
