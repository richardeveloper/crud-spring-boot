package br.com.crud.repositories;

import br.com.crud.entities.ProdutoEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

  @Query(value = "SELECT produto FROM ProdutoEntity produto WHERE UPPER(produto.nome) LIKE UPPER(CONCAT('%', :nome, '%'))")
  Optional<ProdutoEntity> findByNome(@Param("nome") String nome);

}
