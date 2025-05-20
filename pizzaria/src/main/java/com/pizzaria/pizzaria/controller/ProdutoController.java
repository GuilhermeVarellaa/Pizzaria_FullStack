package com.pizzaria.pizzaria.controller;


import com.pizzaria.pizzaria.model.Produto;
import com.pizzaria.pizzaria.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService){
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos(){
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id){
        return produtoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Produto>> bucarPorCategoria(@PathVariable String categoria){
        return ResponseEntity.ok(produtoService.buscarPorCategoria(categoria));
    }

    @GetMapping("/busca")
    public ResponseEntity<List<Produto>> buscarPorNome (String nome){
        return ResponseEntity.ok(produtoService.buscarPorNome(nome));
    }


    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody Produto produto){
        Produto novoProduto = produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar (@PathVariable Long id, @RequestBody Produto produto){
        return produtoService.buscarPorId(id)
                .map(produtoExistente -> {
                    produto.setId(id);
                    return ResponseEntity.ok(produtoService.salvar(produto));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable long id){
        return produtoService.buscarPorId(id)
                .map(produto -> {
                    produtoService.excluir(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
