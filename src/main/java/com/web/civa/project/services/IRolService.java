package com.web.civa.project.services;

import com.web.civa.project.models.Rol;

import java.util.Optional;

public interface IRolService {
    Optional<Rol> getRolById(Integer id);
}
