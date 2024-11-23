package com.web.civa.project.services.implementation;

import com.web.civa.project.models.Bus;
import com.web.civa.project.repository.IBusRepository;
import com.web.civa.project.services.IBusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BusService implements IBusService {

    @Autowired
    IBusRepository busRepository;

    @Override
    public Page<Bus> getAllBuses(Pageable pageable) {
        return busRepository.findAll(pageable);
    }

    @Override
    public Bus getBusById(Integer id) {
        return busRepository.findById(id).orElse(null);
    }

    @Override
    public Bus getBusByPlaca(String placa) {
        return busRepository.findByPlaca(placa);
    }

    @Override
    public Boolean saveBus(Bus bus) {
        boolean response;
        try {
            busRepository.save(bus);
            response = true;
        }catch (Exception e) {
            response = false;
        }

        return response;
    }
}
