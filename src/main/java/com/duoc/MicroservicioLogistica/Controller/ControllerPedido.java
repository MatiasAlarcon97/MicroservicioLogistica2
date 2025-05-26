package com.duoc.MicroservicioLogistica.Controller;

import com.duoc.MicroservicioLogistica.Model.Pedido;
import com.duoc.MicroservicioLogistica.Service.ServicePedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class ControllerPedido {

    @Autowired
    private ServicePedido service;

    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido p) {
        return new ResponseEntity<>(service.guardar(p), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Pedido> listar() {
        return service.listar();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
