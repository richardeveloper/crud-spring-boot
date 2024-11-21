package br.com.crud.models.responses;

import br.com.crud.entities.ClienteEntity;

import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {

  private Long id;

  private String nome;

  private String email;

  private String telefone;

  private String dataCadastro;

  public ClienteResponse(ClienteEntity clienteEntity) {
    this.id = clienteEntity.getId();
    this.nome = clienteEntity.getNome();
    this.email = clienteEntity.getEmail();
    this.telefone = clienteEntity.getTelefone();
    this.dataCadastro = clienteEntity.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
  }

}
