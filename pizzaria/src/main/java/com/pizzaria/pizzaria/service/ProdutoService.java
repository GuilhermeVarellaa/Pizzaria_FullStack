package com.pizzaria.pizzaria.service;

import com.pizzaria.pizzaria.model.Produto;
import com.pizzaria.pizzaria.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos(){
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId (Long id){
        return  produtoRepository.findById(id);
    }

    public List<Produto> buscarPorCategoria(String categoria){
        return produtoRepository.findByCategoria(categoria);
    }

    public List<Produto> buscarPorDisponibilidade(boolean disponivel){
        return produtoRepository.findByDisponivel(disponivel);
    }

    public List<Produto> buscarPorNome(String nome){
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }


    public Produto salvar(Produto produto){
        return produtoRepository.save(produto);
    }

    public void excluir(Long id){
        produtoRepository.deleteById(id);
    }


}
