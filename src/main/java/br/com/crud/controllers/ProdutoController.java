package br.com.crud.controllers;

import br.com.crud.entities.ProdutoEntity;
import br.com.crud.models.requests.ProdutoRequest;
import br.com.crud.models.responses.ProdutoResponse;
import br.com.crud.services.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;
import java.util.List;

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
@RequestMapping(value = "/services/produtos")
public class ProdutoController {

  private final ProdutoService produtoService;

  public ProdutoController(ProdutoService produtoService) {
    this.produtoService = produtoService;
  }

  @Operation(summary = "Cadastrar novo produto")
  @PostMapping
  public ResponseEntity<ProdutoResponse> cadastrarProduto(@RequestBody @Valid ProdutoRequest resquest) {
    ProdutoEntity entity = this.produtoService.cadastrarProduto(resquest);
    ProdutoResponse response = new ProdutoResponse(entity);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Operation(summary = "Buscar produto pelo id")
  @GetMapping(value = "/{id}")
  public ResponseEntity<ProdutoResponse> buscarProduto(@PathVariable Long id) {
    ProdutoEntity entity = this.produtoService.buscarProduto(id);
    ProdutoResponse response = new ProdutoResponse(entity);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @Operation(summary = "Buscar produto pelo nome")
  @GetMapping(value = "/nome")
  public ResponseEntity<List<ProdutoResponse>> buscarProdutosPorNome(@RequestParam(value = "nome") String nome) {
    List<ProdutoEntity> entities = this.produtoService.buscarProdutosPorNome(nome);
    List<ProdutoResponse> response = entities.stream().map(ProdutoResponse::new).toList();

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @Operation(summary = "Buscar todos os produtos")
  @GetMapping
  public ResponseEntity<List<ProdutoResponse>> buscarProdutos() {
    List<ProdutoEntity> entities = this.produtoService.buscarProdutos();
    List<ProdutoResponse> response = entities.stream().map(ProdutoResponse::new).toList();

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @Operation(summary = "Editar produto existente")
  @PutMapping(value = "/{id}")
  public ResponseEntity<ProdutoResponse> editarProduto(@PathVariable Long id, @RequestBody @Valid ProdutoRequest resquest) {
    ProdutoEntity entity = this.produtoService.editarProduto(id, resquest);
    ProdutoResponse response = new ProdutoResponse(entity);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @Operation(summary = "Apagar produto existente")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> apagarProduto(@PathVariable Long id) {
    this.produtoService.apagarProduto(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
