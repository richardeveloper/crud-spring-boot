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
import br.com.crud.exceptions.ServiceException;
import br.com.crud.repositories.ClienteRepository;
import br.com.crud.models.requests.ClienteResquest;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ClienteServiceImplTest {

  @Mock
  private ClienteRepository clienteRepository;

  @InjectMocks
  private ClienteServiceImpl clienteService;

  private Long id;
  private String nome;
  private ClienteEntity entity;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    id = 1L;
    nome = "Cliente";
    
    entity = new ClienteEntity();
    entity.setId(id);
    entity.setNome(nome);
    entity.setEmail("teste@email.com");
    entity.setTelefone("99999999999");
  }

  @Test
  public void cadastrarCliente_deveCadastrarClienteComSucesso() {
    ClienteResquest resquest = new ClienteResquest();
    resquest.setNome("Cliente");
    resquest.setEmail("teste@email.com");
    resquest.setTelefone("99999999999");

    when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(entity);

    ClienteEntity clienteEntity = clienteService.cadastrarCliente(resquest);

    assertEquals("Cliente", clienteEntity.getNome());
    assertEquals("teste@email.com", clienteEntity.getEmail());
    assertEquals("99999999999", clienteEntity.getTelefone());
  }

  @Test
  public void buscarCliente_deveBuscarClientePorIdComSucesso() {
    when(clienteRepository.findById(id)).thenReturn(Optional.of(entity));

    ClienteEntity clienteEntity = clienteService.buscarCliente(id);

    assertEquals("Cliente", clienteEntity.getNome());
    assertEquals("teste@email.com", clienteEntity.getEmail());
    assertEquals("99999999999", clienteEntity.getTelefone());
  }

  @Test
  public void buscarCliente_deveLancarExcecaoQuandoClienteNaoEncontrado() {
    when(clienteRepository.findById(id)).thenReturn(Optional.empty());

    ServiceException exception = assertThrows(ServiceException.class,
      () -> clienteService.buscarCliente(id));

    String message = "Não foi encontrado cliente para o id informado.";

    assertEquals(exception.getMessage(), message);
  }

  @Test
  public void buscarClientePorNome_deveBuscarClientePorNomeComSucesso() {
    when(clienteRepository.findByNome(nome)).thenReturn(Optional.of(entity));

    ClienteEntity clienteEntity = clienteService.buscarClientePorNome(nome);

    assertEquals("Cliente", clienteEntity.getNome());
    assertEquals("teste@email.com", clienteEntity.getEmail());
    assertEquals("99999999999", clienteEntity.getTelefone());
  }

  @Test
  public void buscarClientePorNome_deveLancarExcecaoQuandoClienteNaoEncontrado() {
    when(clienteRepository.findByNome(nome)).thenReturn(Optional.empty());

    ServiceException exception = assertThrows(ServiceException.class,
      () -> clienteService.buscarClientePorNome(nome));

    String message = String.format("Não foi encontrado cliente com nome parecido a '%s'.", nome);

    assertEquals(exception.getMessage(), message);
  }

  @Test
  public void buscarClientes_deveBuscarTodosClientesComSucesso() {
    ClienteEntity entity2 = new ClienteEntity();
    entity2.setId(2L);
    entity2.setNome("Cliente II");
    entity2.setEmail("teste2@email.com");
    entity2.setTelefone("62123456789");

    List<ClienteEntity> entitites = List.of(entity, entity2);

    when(clienteRepository.findAll()).thenReturn(entitites);

    List<ClienteEntity> clientes = clienteService.buscarClientes();

    assertNotNull(clientes);
    assertFalse(clientes.isEmpty());

    assertEquals("Cliente", clientes.get(0).getNome());
    assertEquals("teste@email.com", clientes.get(0).getEmail());
    assertEquals("99999999999", clientes.get(0).getTelefone());

    assertEquals("Cliente II", clientes.get(1).getNome());
    assertEquals("teste2@email.com", clientes.get(1).getEmail());
    assertEquals("62123456789", clientes.get(1).getTelefone());
  }

  @Test
  public void editarCliente_deveEditarTodosCamposComSucesso() {
    ClienteResquest resquest = new ClienteResquest();
    resquest.setNome("Cliente Atualizacao");
    resquest.setEmail("teste.atualizacao@email.com");
    resquest.setTelefone("62987654321");

    when(clienteRepository.findById(id)).thenReturn(Optional.of(entity));

    entity.setNome(resquest.getNome());
    entity.setEmail(resquest.getEmail());
    entity.setTelefone(resquest.getTelefone());

    when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(entity);

    ClienteEntity cliente = clienteService.editarCliente(id, resquest);

    assertEquals("Cliente Atualizacao", cliente.getNome());
    assertEquals("teste.atualizacao@email.com", cliente.getEmail());
    assertEquals("62987654321", cliente.getTelefone());
  }

  @Test
  public void editarCliente_deveEditarCampoNomeComSucesso() {
    ClienteResquest resquest = new ClienteResquest();
    resquest.setNome("Cliente Atualizacao");

    when(clienteRepository.findById(id)).thenReturn(Optional.of(entity));

    entity.setNome(resquest.getNome());

    when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(entity);

    ClienteEntity cliente = clienteService.editarCliente(id, resquest);

    assertEquals("Cliente Atualizacao", cliente.getNome());
  }

  @Test
  public void editarCliente_deveEditarCampoEmailComSucesso() {
    ClienteResquest resquest = new ClienteResquest();
    resquest.setEmail("teste.atualizacao@email.com");

    when(clienteRepository.findById(id)).thenReturn(Optional.of(entity));

    entity.setEmail(resquest.getEmail());

    when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(entity);

    ClienteEntity cliente = clienteService.editarCliente(id, resquest);

    assertEquals("teste.atualizacao@email.com", cliente.getEmail());
  }

  @Test
  public void editarCliente_deveEditarCampoTelefoneComSucesso() {
    ClienteResquest resquest = new ClienteResquest();
    resquest.setTelefone("62987654321");

    when(clienteRepository.findById(id)).thenReturn(Optional.of(entity));

    entity.setTelefone(resquest.getTelefone());

    when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(entity);

    ClienteEntity cliente = clienteService.editarCliente(id, resquest);

    assertEquals("62987654321", cliente.getTelefone());
  }

  @Test
  public void editarCliente_deveLancarExcecaoQuandoClienteNaoEncontrado() {
    when(clienteRepository.findById(id)).thenReturn(Optional.empty());

    ServiceException exception = assertThrows(ServiceException.class,
      () -> clienteService.editarCliente(id, new ClienteResquest()));

    String message = "Não foi encontrado cliente para o id informado.";

    assertEquals(exception.getMessage(), message);
  }

  @Test
  public void apagarCliente_deveApagarClinteComSucesso() {
    when(clienteRepository.findById(id)).thenReturn(Optional.ofNullable(entity));

    clienteService.apagarCliente(id);

    verify(clienteRepository, times(1)).delete(entity);
  }

}