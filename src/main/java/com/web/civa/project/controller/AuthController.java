package com.web.civa.project.controller;

import com.web.civa.project.models.Rol;
import com.web.civa.project.models.Usuario;
import com.web.civa.project.models.request.RegisterRequest;
import com.web.civa.project.models.request.AuthRequest;
import com.web.civa.project.models.response.ApiResponse;
import com.web.civa.project.models.response.AuthResponse;
import com.web.civa.project.services.IUsuarioService;
import com.web.civa.project.services.implementation.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private RolService rolService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest)
    {
        try {
            if (authRequest.getNombreUsuario() == null || authRequest.getNombreUsuario().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResponse(
                                "FAIL",
                                "El campo nombre de usuario es obligatorio."
                        )
                );
            }

            if (authRequest.getClave() == null || authRequest.getClave().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResponse(
                                "FAIL",
                                "El campo clave es obligatorio."
                        )
                );
            }

            Optional<Usuario> usuarioOptional = usuarioService.getUsuarioByNombreUsuario(authRequest.getNombreUsuario());

            if (!usuarioOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        new ApiResponse(
                                "FAIL",
                                "No existe un usuario con esas credenciales."
                        )
                );
            }

            AuthResponse authResponse = usuarioService.login(usuarioOptional.get());

            if (authResponse == null ) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse(
                                "FAIL",
                                "Ocurrio un error al autenticar al usuario."
                        )
                );
            }

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse(
                            "SUCCESS",
                            authResponse
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor: " + e.getMessage());
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> saveUser(@RequestBody RegisterRequest registerRequest)
    {
        try {

            if (registerRequest.getNombreUsuario() == null || registerRequest.getNombreUsuario().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResponse(
                                "FAIL",
                                "El campo nombre de usuario es obligatorio."
                        )
                );
            }

            if (registerRequest.getClave() == null || registerRequest.getClave().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResponse(
                                "FAIL",
                                "El campo clave es obligatorio."
                        )
                );
            }

            if (registerRequest.getIdRol() == null || registerRequest.getIdRol() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResponse(
                                "FAIL",
                                "El campo idRol es obligatorio y debe ser mayor a 0."
                        )
                );
            }

            Optional<Rol> rolOptional = rolService.getRolById(registerRequest.getIdRol());
            if (!rolOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse(
                                "FAIL",
                                "No existe un rol con ese idRol."
                        )
                );
            }

            Optional<Usuario> usuarioOptional = usuarioService.getUsuarioByNombreUsuario(registerRequest.getNombreUsuario());

            if (usuarioOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.FOUND).body(
                        new ApiResponse(
                                "FAIL",
                                "Ya existe un usuario con esas credenciales."
                        )
                );
            }

            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(registerRequest.getNombreUsuario());
            usuario.setClave(registerRequest.getClave());
            usuario.setRol(rolOptional.get());

            AuthResponse authResponse = usuarioService.saveUser(usuario);

            if (authResponse == null ) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse(
                                "FAIL",
                                "Ocurrio un error al registrar al usuario."
                        )
                );
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse(
                            "SUCCESS",
                            authResponse
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor: " + e.getMessage());
        }
    }
}
