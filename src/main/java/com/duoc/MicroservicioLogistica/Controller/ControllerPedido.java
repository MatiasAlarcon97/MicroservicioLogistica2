package com.duoc.MicroservicioLogistica.Controller;


import com.duoc.MicroservicioLogistica.Model.Pedido;
import com.duoc.MicroservicioLogistica.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class ControllerPedido {

    @Autowired
    private PedidoService service;

    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido p) {
        return new ResponseEntity<>(service.guardar(p), HttpStatus.CREATED);
    }

    @PostMapping("/con-envio")
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedido) {
        Pedido creado = service.crear(pedido);
        return ResponseEntity.ok(creado);
    }

    @GetMapping
    public List<Pedido> listar() {
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
