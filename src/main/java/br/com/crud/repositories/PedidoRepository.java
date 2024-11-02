package br.com.crud.repositories;

import br.com.crud.entities.ClienteEntity;
import br.com.crud.entities.PedidoEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

  List<PedidoEntity> findByCliente(ClienteEntity cliente);

}
