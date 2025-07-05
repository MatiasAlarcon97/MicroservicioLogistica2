package com.duoc.MicroservicioLogistica.MicroservicioLogistica.Controller;

import com.duoc.MicroservicioLogistica.Controller.PedidoEnvioController;
import com.duoc.MicroservicioLogistica.Model.Envio;
import com.duoc.MicroservicioLogistica.Model.Pedido;
import com.duoc.MicroservicioLogistica.Model.Proveedor;
import com.duoc.MicroservicioLogistica.Service.EnvioService;
import com.duoc.MicroservicioLogistica.Service.PedidoService;
import com.duoc.MicroservicioLogistica.Service.ProveedorService;
import com.duoc.MicroservicioLogistica.dto.PedidoConEnvioDTO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoEnvioControllerTest {

    @Mock
    private EnvioService envioService;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private ProveedorService proveedorService;

    @InjectMocks
    private PedidoEnvioController pedidoEnvioController;

    private PedidoConEnvioDTO pedidoConEnvioDTO;
    private Envio envio;
    private Pedido pedido;
    private Proveedor proveedor;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba
        proveedor = new Proveedor();
        proveedor.setId(1L);
        proveedor.setNombre("Proveedor Default");

        envio = new Envio();
        envio.setId(1L);
        envio.setDireccion_entrega("Calle Principal 123");
        envio.setFecha_envio(LocalDate.now().plusDays(2));
        envio.setProveedor(proveedor);

        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setFecha_creacion(LocalDate.now());
        pedido.setEstado("PENDIENTE");
        pedido.setEnvio(envio);

        pedidoConEnvioDTO = new PedidoConEnvioDTO();
        pedidoConEnvioDTO.setDireccion_entrega("Calle Principal 123");
        pedidoConEnvioDTO.setFecha_envio(LocalDate.now().plusDays(2));
        pedidoConEnvioDTO.setEstado_pedido("PENDIENTE");
    }

    @Test
    void crearPedidoYEnvio_DeberiaRetornarCreated() {
        // Configurar mocks
        when(proveedorService.obtenerProveedorPorDefecto()).thenReturn(proveedor);
        when(envioService.guardar(any(Envio.class))).thenReturn(envio);
        when(pedidoService.guardar(any(Pedido.class))).thenReturn(pedido);

        // Ejecutar
        ResponseEntity<?> response = pedidoEnvioController.crearPedidoYEnvioDesdeVenta(pedidoConEnvioDTO);

        // Verificar
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verificar interacciones
        verify(proveedorService).obtenerProveedorPorDefecto();
        verify(envioService).guardar(any(Envio.class));
        verify(pedidoService).guardar(any(Pedido.class));
    }

    @Test
    void listarPedidosConEnvio_DeberiaRetornarLista() {
        // Configurar mock
        Pedido pedido2 = new Pedido();
        pedido2.setId(2L);
        List<Pedido> pedidos = Arrays.asList(pedido, pedido2);
        when(pedidoService.listarTodos()).thenReturn(pedidos);

        // Ejecutar
        ResponseEntity<List<Pedido>> response = pedidoEnvioController.listarPedidosConEnvio();

        // Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(pedidoService, times(1)).listarTodos();
    }

    @Test
    void crearPedidoYEnvio_ConDatosInvalidos_DeberiaManejarError() {
        // Configurar mock para lanzar excepción
        when(proveedorService.obtenerProveedorPorDefecto())
                .thenThrow(new RuntimeException("Error al obtener proveedor"));

        // Ejecutar y verificar
        assertThrows(RuntimeException.class, () -> {
            pedidoEnvioController.crearPedidoYEnvioDesdeVenta(pedidoConEnvioDTO);
        });
    }

    @Test
    void listarPedidosConEnvio_Vacio_DeberiaRetornarListaVacia() {
        // Configurar mock para lista vacía
        when(pedidoService.listarTodos()).thenReturn(List.of());

        // Ejecutar
        ResponseEntity<List<Pedido>> response = pedidoEnvioController.listarPedidosConEnvio();

        // Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void crearPedidoYEnvio_DeberiaAsociarEnvioAPedido() {
        // Configurar mocks
        when(proveedorService.obtenerProveedorPorDefecto()).thenReturn(proveedor);
        when(envioService.guardar(any(Envio.class))).thenReturn(envio);

        // Capturar el pedido guardado para verificar la asociación
        when(pedidoService.guardar(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido p = invocation.getArgument(0);
            assertNotNull(p.getEnvio());
            assertEquals(envio.getId(), p.getEnvio().getId());
            return pedido;
        });

        // Ejecutar
        ResponseEntity<?> response = pedidoEnvioController.crearPedidoYEnvioDesdeVenta(pedidoConEnvioDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
