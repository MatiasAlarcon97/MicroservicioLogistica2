package com.duoc.MicroservicioLogistica.MicroservicioLogistica.Controller;


import com.duoc.MicroservicioLogistica.Controller.ControllerPedido;
import com.duoc.MicroservicioLogistica.Model.Envio;
import com.duoc.MicroservicioLogistica.Model.Pedido;
import com.duoc.MicroservicioLogistica.Service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ControllerPedidoTest {

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private ControllerPedido controllerPedido;

    private Pedido pedido;
    private Envio envio;

    @BeforeEach
    void setUp() {
        envio = new Envio();
        envio.setId(1L);
        envio.setDireccion_entrega("Calle Principal 123");

        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setFecha_creacion(LocalDate.now());
        pedido.setEstado("PENDIENTE");
        pedido.setId_venta(100L);
        pedido.setEnvio(envio);
    }

    @Test
    void crearPedido_DeberiaRetornarCreated() {
        // Configurar mock
        when(pedidoService.guardar(any(Pedido.class))).thenReturn(pedido);

        // Ejecutar
        ResponseEntity<Pedido> response = controllerPedido.crear(pedido);

        // Verificar
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("PENDIENTE", response.getBody().getEstado());
        verify(pedidoService, times(1)).guardar(any(Pedido.class));
    }

    @Test
    void crearPedidoConEnvio_DeberiaRetornarOk() {
        // Configurar mock
        when(pedidoService.crear(any(Pedido.class))).thenReturn(pedido);

        // Ejecutar
        ResponseEntity<Pedido> response = controllerPedido.crearPedido(pedido);

        // Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(pedidoService, times(1)).crear(any(Pedido.class));
    }

    @Test
    void listarPedidos_DeberiaRetornarLista() {
        // Configurar mock
        Pedido pedido2 = new Pedido();
        pedido2.setId(2L);
        List<Pedido> pedidos = Arrays.asList(pedido, pedido2);
        when(pedidoService.listar()).thenReturn(pedidos);

        // Ejecutar
        List<Pedido> resultado = controllerPedido.listar();

        // Verificar
        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        assertEquals(2L, resultado.get(1).getId());
        verify(pedidoService, times(1)).listar();
    }

    @Test
    void eliminarPedido_Existente_DeberiaRetornarNoContent() {
        // Configurar mock
        when(pedidoService.existe(1L)).thenReturn(true);
        doNothing().when(pedidoService).eliminar(1L);

        // Ejecutar
        ResponseEntity<Void> response = controllerPedido.eliminar(1L);

        // Verificar
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(pedidoService, times(1)).existe(1L);
        verify(pedidoService, times(1)).eliminar(1L);
    }

    @Test
    void eliminarPedido_NoExistente_DeberiaRetornarNotFound() {
        // Configurar mock
        when(pedidoService.existe(99L)).thenReturn(false);

        // Ejecutar
        ResponseEntity<Void> response = controllerPedido.eliminar(99L);

        // Verificar
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(pedidoService, times(1)).existe(99L);
        verify(pedidoService, never()).eliminar(anyLong());
    }

    @Test
    void crearPedido_ConDatosInvalidos_DeberiaManejarError() {
        // Configurar mock
        when(pedidoService.guardar(any(Pedido.class)))
                .thenThrow(new IllegalArgumentException("Datos de pedido inválidos"));

        // Ejecutar y verificar
        assertThrows(IllegalArgumentException.class, () -> {
            controllerPedido.crear(new Pedido());
        });
    }

    @Test
    void listarPedidos_Vacio_DeberiaRetornarListaVacia() {
        // Configurar mock
        when(pedidoService.listar()).thenReturn(List.of());

        // Ejecutar
        List<Pedido> resultado = controllerPedido.listar();

        // Verificar
        assertTrue(resultado.isEmpty());
        verify(pedidoService, times(1)).listar();
    }
}
