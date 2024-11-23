package br.com.crud.repositories;

import br.com.crud.entities.ClienteEntity;
import br.com.crud.entities.PedidoEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

  @Query(value = "SELECT pedido FROM PedidoEntity pedido ORDER BY pedido.dataPedido DESC")
  List<PedidoEntity> findAllOrderByDataPedidoDesc();

  @Query(value = "SELECT pedido FROM PedidoEntity pedido WHERE pedido.cliente = :cliente ORDER BY pedido.dataPedido DESC")
  List<PedidoEntity> findByClienteOrderByDataPedidoDesc(ClienteEntity cliente);

}
