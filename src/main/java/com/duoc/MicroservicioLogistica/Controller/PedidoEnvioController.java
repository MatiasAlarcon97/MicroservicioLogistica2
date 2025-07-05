package com.duoc.MicroservicioLogistica.Controller;

import com.duoc.MicroservicioLogistica.Model.Envio;
import com.duoc.MicroservicioLogistica.Model.Pedido;
import com.duoc.MicroservicioLogistica.Service.EnvioService;
import com.duoc.MicroservicioLogistica.Service.PedidoService;
import com.duoc.MicroservicioLogistica.Service.ProveedorService;
import com.duoc.MicroservicioLogistica.dto.PedidoConEnvioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pedidos-envio")
public class PedidoEnvioController {

    @Autowired
    private EnvioService envioService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<?> crearPedidoYEnvioDesdeVenta(@RequestBody PedidoConEnvioDTO dto) {

        // 1. Crear Envío
        Envio envio = new Envio();
        envio.setDireccion_entrega(dto.getDireccion_entrega());
        envio.setFecha_envio(dto.getFecha_envio());

        // Se asocia a un proveedor (puedes ajustar esto según tu lógica)
        envio.setProveedor(proveedorService.obtenerProveedorPorDefecto());

        Envio envioGuardado = envioService.guardar(envio);

        // 2. Crear Pedido asociado a ese Envío
        Pedido pedido = new Pedido();
        pedido.setFecha_creacion(LocalDate.now());
        pedido.setEstado(dto.getEstado_pedido());
        pedido.setEnvio(envioGuardado);

        pedidoService.guardar(pedido);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidosConEnvio() {
        List<Pedido> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

}

