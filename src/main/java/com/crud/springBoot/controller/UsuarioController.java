package com.crud.springBoot.controller;

import java.util.Optional;

import javax.validation.Valid;

import com.crud.springBoot.domain.Usuario;
import com.crud.springBoot.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/inscricao")
    public String mostrarFormularioDeInscricao(Usuario usuario) {
        return "adicionar-usuario";
    }

    @PostMapping("/adicionarUsuario")
    public String adiocionarUsuario(@Valid Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "adicionar-usuario";
        }

        usuarioRepository.save(usuario);
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "index";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") long id, Model model){
        Usuario usuario  = usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário inválido ID:" + id));

        model.addAttribute("usuario", usuario);
        return "editar-usuario";
    }

    @PostMapping("/editarUsuario/{id}")
    public String editarUsuario(@PathVariable("id") long id, @Valid Usuario usuario, BindingResult result, Model model){
        if(result.hasErrors()){
            usuario.setId(id);
            return "editar-usuario";
        }

        usuarioRepository.save(usuario);
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "index";
    }

    @GetMapping("/excluir/{id}")
    public String excluirUsuario(@PathVariable("id") long id, Model model){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário inválido ID:" + id));
        usuarioRepository.delete(usuario);
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "index";
    }

}