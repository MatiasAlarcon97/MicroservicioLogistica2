package com.duoc.MicroservicioLogistica.Controller;

import com.duoc.MicroservicioLogistica.Model.Envio;
import com.duoc.MicroservicioLogistica.Service.ServiceEnvio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/envios")
public class ControllerEnvio {

    @Autowired
    private ServiceEnvio service;

    @PostMapping
    public ResponseEntity<Envio> crear(@RequestBody Envio e) {
        return new ResponseEntity<>(service.guardar(e), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Envio> listar() {
        return service.listar();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!service.existe(id)) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
