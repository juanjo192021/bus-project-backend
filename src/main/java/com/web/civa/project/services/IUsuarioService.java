package com.web.civa.project.services;

import com.web.civa.project.models.Usuario;
import com.web.civa.project.models.request.RegisterRequest;
import com.web.civa.project.models.request.AuthRequest;
import com.web.civa.project.models.response.AuthResponse;

import java.util.Optional;

public interface IUsuarioService {

    AuthResponse login(Usuario usuario);
    AuthResponse saveUser(Usuario usuario);

    Optional<Usuario> getUsuarioByNombreUsuario(String nombreUsuario);
}
