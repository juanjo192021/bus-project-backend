package com.web.civa.project.services;

import com.web.civa.project.models.request.RegisterRequest;
import com.web.civa.project.models.request.AuthRequest;
import com.web.civa.project.models.response.AuthResponse;

public interface IUsuarioService {

    AuthResponse login(AuthRequest request);

    AuthResponse saveUser(RegisterRequest request);
}
