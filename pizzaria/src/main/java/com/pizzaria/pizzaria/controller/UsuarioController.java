package com.pizzaria.pizzaria.controller;


import com.pizzaria.pizzaria.model.Usuario;
import com.pizzaria.pizzaria.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos(){
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("{id}")
    public ResponseEntity<Usuario>buscarPorId(@PathVariable Long id){
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?>criar(@RequestBody Usuario usuario){
        if (usuarioService.existeUsername(usuario.getUsername())){
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("Username já está em uso");
        }
            Usuario novoUsuario = usuarioService.salvar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar (@PathVariable Long id, @RequestBody Usuario usuario){
        return usuarioService.buscarPorId(id)
                .map(usuarioExistente ->{
                    if (!usuarioExistente.getUsername().equals(usuario.getUsername()) &&
                    usuarioService.existeUsername(usuario.getUsername())){
                        return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body("Username já está em uso");
                    }

                    usuario.setId(id);
                    return ResponseEntity.ok(usuarioService.salvar(usuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        return usuarioService.buscarPorId(id)
                .map(usuario -> {
                    usuarioService.excluir(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
