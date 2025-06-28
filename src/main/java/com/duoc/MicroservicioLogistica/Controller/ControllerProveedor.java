package com.duoc.MicroservicioLogistica.Controller;

import com.duoc.MicroservicioLogistica.Model.Proveedor;
import com.duoc.MicroservicioLogistica.Service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
public class ControllerProveedor {

    @Autowired
    private ProveedorService service;

    @PostMapping
    public ResponseEntity<Proveedor> crear(@RequestBody Proveedor p) {
        return new ResponseEntity<>(service.guardar(p), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Proveedor> listar() {
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