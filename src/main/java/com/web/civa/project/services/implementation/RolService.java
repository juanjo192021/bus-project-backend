package com.web.civa.project.services.implementation;

import com.web.civa.project.models.Rol;
import com.web.civa.project.repository.IRolRepository;
import com.web.civa.project.services.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolService implements IRolService {

    @Autowired
    IRolRepository rolRepository;

    @Override
    public Optional<Rol> getRolById(Integer id) {
        return rolRepository.findById(id);
    }
}
