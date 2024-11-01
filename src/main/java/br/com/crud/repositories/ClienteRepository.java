package br.com.crud.repositories;

import br.com.crud.entities.ClienteEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

  @Query(value = "SELECT cliente FROM ClienteEntity cliente WHERE UPPER(cliente.nome) LIKE UPPER(CONCAT('%', :nome, '%'))")
  Optional<ClienteEntity> findByNome(@Param("nome") String nome);

}
