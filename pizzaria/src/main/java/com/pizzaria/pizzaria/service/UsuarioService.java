package com.pizzaria.pizzaria.service;


import com.pizzaria.pizzaria.model.Usuario;
import com.pizzaria.pizzaria.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId (Long id){
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorUsername (String username){
        return usuarioRepository.findByUsername(username);
    }

    public Usuario salvar (Usuario usuario){
        String senhaCripitografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCripitografada);
        return usuarioRepository.save(usuario);
    }

    public boolean existeUsername(String username){
        return usuarioRepository.existsByUsername(username);
    }

    public void excluir(Long id){
        usuarioRepository.deleteById(id);
    }


}
