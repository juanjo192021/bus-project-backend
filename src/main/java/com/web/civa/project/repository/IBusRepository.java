package com.web.civa.project.repository;

import com.web.civa.project.models.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBusRepository extends JpaRepository<Bus,Integer> {

    public Bus findByPlaca(String placa);
}
