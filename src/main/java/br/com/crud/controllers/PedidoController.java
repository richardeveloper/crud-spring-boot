package br.com.crud.controllers;

import br.com.crud.entities.PedidoEntity;
import br.com.crud.requests.PedidoResquest;
import br.com.crud.responses.PedidoResponse;
import br.com.crud.services.PedidoService;

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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/services/pedidos")
public class PedidoController {

  @Autowired
  private PedidoService produtoService;

  @Operation(summary = "Cadastrar novo pedido")
  @PostMapping
  public ResponseEntity<PedidoResponse> cadastrarPedido(@RequestBody @Valid PedidoResquest resquest) {
    PedidoEntity entity = this.produtoService.cadastrarPedido(resquest);
    PedidoResponse response = new PedidoResponse(entity);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Operation(summary = "Buscar pedido pelo id")
  @GetMapping(value = "/{id}")
  public ResponseEntity<PedidoResponse> buscarPedido(@PathVariable(value = "id") Long id) {
    PedidoEntity entity = this.produtoService.buscarPedido(id);
    PedidoResponse response = new PedidoResponse(entity);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @Operation(summary = "Buscar pedido pelo cliente")
  @GetMapping(value = "/cliente/{clienteId}")
  public ResponseEntity<List<PedidoResponse>> buscarPedidosPorCliente(@PathVariable(value = "clienteId") Long clienteId) {
    List<PedidoEntity> entities = this.produtoService.buscarPedidosPorCliente(clienteId);
    List<PedidoResponse> responses = entities.stream().map(PedidoResponse::new).toList();

    return ResponseEntity.status(HttpStatus.OK).body(responses);
  }

  @Operation(summary = "Buscar todos os pedidos")
  @GetMapping
  public ResponseEntity<List<PedidoResponse>> buscarPedidos() {
    List<PedidoEntity> entities = this.produtoService.buscarPedidos();
    List<PedidoResponse> responses = entities.stream().map(PedidoResponse::new).toList();

    return ResponseEntity.status(HttpStatus.OK).body(responses);
  }

  @Operation(summary = "Editar pedido existente")
  @PutMapping(value = "/{id}")
  public ResponseEntity<PedidoResponse> editarPedido(@PathVariable(value = "id") Long id, @RequestBody @Valid PedidoResquest resquest) {
    PedidoEntity entity = this.produtoService.editarPedido(id, resquest);
    PedidoResponse response = new PedidoResponse(entity);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @Operation(summary = "Apagar pedido existente")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> apagarPedido(@PathVariable Long id) {
    this.produtoService.apagarPedido(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
