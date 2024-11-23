package com.web.civa.project.services.implementation;

import com.web.civa.project.jwt.JwtTokenFilter;
import com.web.civa.project.jwt.JwtTokenUtil;
import com.web.civa.project.models.Rol;
import com.web.civa.project.models.Usuario;
import com.web.civa.project.models.dto.UsuarioDto;
import com.web.civa.project.models.request.RegisterRequest;
import com.web.civa.project.models.request.AuthRequest;
import com.web.civa.project.models.response.AuthResponse;
import com.web.civa.project.repository.IRolRepository;
import com.web.civa.project.repository.IUsuarioRepository;
import com.web.civa.project.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @Autowired
    private JwtTokenFilter jwtFilter;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IRolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public AuthResponse login(Usuario usuario) {
        AuthResponse authResponse;
        try {
            String accessToken = jwtUtil.generarToken(usuario);
            UsuarioDto usuarioDTO = new UsuarioDto();
            usuarioDTO.setNombreUsuario(usuario.getNombreUsuario());
            usuarioDTO.setNombreRol(usuario.getRol().getNombre());
            authResponse = new AuthResponse(usuarioDTO, accessToken);

        }catch (Exception e) {
            authResponse = null;
        }
        return authResponse;
    }

    @Override
    public AuthResponse saveUser(Usuario usuario) {
        AuthResponse authResponse;
        try {
            usuario.setClave(passwordEncoder.encode(usuario.getClave()));
            usuarioRepository.save(usuario);
            String accessToken = jwtUtil.generarToken(usuario);
            UsuarioDto usuarioDTO = new UsuarioDto(usuario.getNombreUsuario(), usuario.getRol().getNombre());
            authResponse = new AuthResponse(usuarioDTO, accessToken);
        } catch (Exception e) {
            authResponse = null;
        }
        return authResponse;
    }

    @Override
    public Optional<Usuario> getUsuarioByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }
}
