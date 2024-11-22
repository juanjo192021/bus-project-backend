package com.web.civa.project.services.implementation;

import com.web.civa.project.models.Marca;
import com.web.civa.project.repository.IMarcaRepository;
import com.web.civa.project.services.IMarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarcaService implements IMarcaService {

    @Autowired
    IMarcaRepository marcaRepository;

    @Override
    public Marca getMarcaById(Integer id) {
        return marcaRepository.findById(id).orElse(null);
    }
}
