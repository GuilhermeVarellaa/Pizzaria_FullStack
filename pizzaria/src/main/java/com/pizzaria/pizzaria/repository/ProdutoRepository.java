package com.pizzaria.pizzaria.repository;

import com.pizzaria.pizzaria.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository <Produto, Long>{

    List<Produto> findByCategoria(String categoria);
    List<Produto> findByDisponivel(boolean disponivel);
    List<Produto> findByNomeContainingIgnoreCase(String nome);

}
