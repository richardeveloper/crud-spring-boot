package br.com.crud.controllers;

import br.com.crud.entities.ClienteEntity;
import br.com.crud.models.requests.ClienteResquest;
import br.com.crud.models.responses.ClienteResponse;
import br.com.crud.services.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/services/clientes")
public class ClienteController {

  @Autowired
  private ClienteService produtoService;

  @Operation(summary = "Cadastrar novo cliente")
  @PostMapping
  public ResponseEntity<ClienteResponse> cadastrarCliente(@RequestBody @Valid ClienteResquest resquest) {
    ClienteEntity entity = this.produtoService.cadastrarCliente(resquest);
    ClienteResponse response = new ClienteResponse(entity);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Operation(summary = "Buscar cliente pelo id")
  @GetMapping(value = "/{id}")
  public ResponseEntity<ClienteResponse> buscarCliente(@PathVariable(value = "id") Long id) {
    ClienteEntity entity = this.produtoService.buscarCliente(id);
    ClienteResponse response = new ClienteResponse(entity);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @Operation(summary = "Buscar cliente pelo nome")
  @GetMapping(value = "/nome")
  public ResponseEntity<ClienteResponse> buscarCliente(@RequestParam(value = "nome") String nome) {
    ClienteEntity entity = this.produtoService.buscarClientePorNome(nome);
    ClienteResponse response = new ClienteResponse(entity);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @Operation(summary = "Buscar todos os clientes")
  @GetMapping
  public ResponseEntity<List<ClienteResponse>> buscarClientes() {
    List<ClienteEntity> entities = this.produtoService.buscarClientes();
    List<ClienteResponse> responses = entities.stream().map(ClienteResponse::new).toList();

    return ResponseEntity.status(HttpStatus.OK).body(responses);
  }

  @Operation(summary = "Editar cliente existente")
  @PutMapping(value = "/{id}")
  public ResponseEntity<ClienteResponse> editarCliente(@PathVariable(value = "id") Long id, @RequestBody @Valid ClienteResquest resquest) {
    ClienteEntity entity = this.produtoService.editarCliente(id, resquest);
    ClienteResponse response = new ClienteResponse(entity);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @Operation(summary = "Apagar cliente existente")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> apagarCliente(@PathVariable Long id) {
    this.produtoService.apagarCliente(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
