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
    public AuthResponse login(AuthRequest request) {
        AuthResponse authResponse = null;
        Optional<Usuario> usuarioOptional = usuarioRepository.findByNombreUsuario(request.getNombreUsuario());
        if(usuarioOptional.isPresent()) {
            Usuario user = usuarioOptional.get();

            String accessToken = jwtUtil.generarToken(user);

            UsuarioDto usuarioDTO = new UsuarioDto();

            usuarioDTO.setNombreUsuario(user.getNombreUsuario());
            usuarioDTO.setNombreRol(user.getRol().getNombre());

            AuthResponse response = new AuthResponse(usuarioDTO, accessToken);

            return response;
        }
        return authResponse;
    }

    @Override
    public AuthResponse saveUser(RegisterRequest request) {
        AuthResponse authResponse = null;
        Optional<Usuario> usuarioOptional = usuarioRepository.findByNombreUsuario(request.getNombreUsuario());
        if(usuarioOptional.isEmpty()) {
            Optional<Rol> rol = rolRepository.findById(request.getIdRol());
            Usuario user = new Usuario();
            user.setNombreUsuario(request.getNombreUsuario());
            user.setClave(passwordEncoder.encode(request.getClave()));
            user.setRol(rol.get());

            usuarioRepository.save(user);

            String accessToken = jwtUtil.generarToken(user);

            UsuarioDto usuarioDTO = new UsuarioDto(request.getNombreUsuario(), rol.get().getNombre());

            AuthResponse response = new AuthResponse(usuarioDTO, accessToken);

            return response;
        }
        return authResponse;

    }
}
