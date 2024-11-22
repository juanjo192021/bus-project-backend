package com.web.civa.project.services;

import com.web.civa.project.models.Bus;
import com.web.civa.project.models.request.BusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBusService {

    Page<Bus> getAllBuses(Pageable pageable);
    Bus getBusById(Integer id);
    Bus getBusByPlaca(String placa);
    Boolean saveBus(Bus bus);
}
