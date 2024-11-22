package com.web.civa.project.controller;

import com.web.civa.project.models.Bus;
import com.web.civa.project.models.Marca;
import com.web.civa.project.models.request.BusRequest;
import com.web.civa.project.models.response.ApiResponse;
import com.web.civa.project.models.dto.PageDto;
import com.web.civa.project.services.IBusService;
import com.web.civa.project.services.IMarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/bus")
public class BusController {

    @Autowired
    private IBusService busService;

    @Autowired
    private IMarcaService marcaService;

    @GetMapping
    public ResponseEntity<?> getAllBuses(@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "5") int size) {
        try {
            if (page < 0 || size <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResponse(
                                "FAIL",
                                "Los parámetros 'page' y 'size' deben ser mayores que cero."
                        )
                );
            }

            Pageable pageable = PageRequest.of(page, size);
            Page<Bus> buses = busService.getAllBuses(pageable);

            if (buses == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse(
                                "FAIL",
                                "No se encontraron buses."
                        )
                );
            }

            PageDto<Bus> pageDTO = new PageDto<>(
                    buses.getContent(),
                    buses.getNumber(),
                    buses.getSize(),
                    buses.getTotalElements(),
                    buses.getTotalPages()
            );

            return ResponseEntity.ok(new ApiResponse(
                    "SUCCESS",
                    pageDTO
            ));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error al procesar la solicitud: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBusById(@PathVariable(name = "id") Integer id) {
        try {
            if (id <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResponse(
                                "FAIL",
                                "El parámetro 'id' debe ser mayor que cero."
                        )
                );
            }

            Bus bus = busService.getBusById(id);

            if (bus == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse(
                                "FAIL",
                                "No se encontró el bus solicitado."
                        )
                );
            }

            return ResponseEntity.ok(new ApiResponse(
                    "SUCCESS",
                    bus
            ));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error al procesar la solicitud: " + e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveBus(@RequestBody BusRequest busRequest) {
        try {

            if (busRequest.getNumeroBus() == null || busRequest.getNumeroBus().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResponse(
                                "FAIL",
                                "El El campo númeroBus es obligatorio."
                        )
                );
            }
            if (busRequest.getCaracteristicas() == null || busRequest.getCaracteristicas().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResponse(
                                "FAIL",
                                "El campo características es obligatorio."
                        )
                );
            }

            if (busRequest.getEstado() == null || busRequest.getEstado().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResponse(
                                "FAIL",
                                "El campo estado es obligatorio."
                        )
                );
            }

            Bus busFindByPlaca = busService.getBusByPlaca(busRequest.getPlaca());

            if (busFindByPlaca != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResponse(
                                "FAIL",
                                "Ya existe un bus con esa placa."
                        )
                );
            }

            Marca marcaFindById = marcaService.getMarcaById(busRequest.getMarcaId());

            if (marcaFindById == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResponse(
                                "FAIL",
                                "No existe una marca con ese id."
                        )
                );
            }

            Bus bus = new Bus();
            bus.setNumeroBus(busRequest.getNumeroBus());
            bus.setPlaca(busRequest.getPlaca());
            bus.setFechaCreacion(LocalDateTime.now());
            bus.setCaracteristicas(busRequest.getCaracteristicas());
            bus.setMarca(marcaFindById);
            bus.setEstado(busRequest.getEstado());

            Boolean response = busService.saveBus(bus);

            if (!response) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse(
                                "FAIL",
                                "Ocurrio un error al guarda el bus."
                        )
                );
            }

            return ResponseEntity.ok(new ApiResponse(
                    "SUCCESS",
                    "Bus creado correctamente."
            ));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error al procesar la solicitud: " + e.getMessage());
        }
    }
}
