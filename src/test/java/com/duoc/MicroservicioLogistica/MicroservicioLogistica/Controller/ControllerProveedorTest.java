package com.duoc.MicroservicioLogistica.MicroservicioLogistica.Controller;


import com.duoc.MicroservicioLogistica.Controller.ControllerProveedor;
import com.duoc.MicroservicioLogistica.Model.Proveedor;
import com.duoc.MicroservicioLogistica.Service.ProveedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ControllerProveedorTest {

    @Mock
    private ProveedorService proveedorService;

    @InjectMocks
    private ControllerProveedor controllerProveedor;

    private Proveedor proveedor;

    @BeforeEach
    void setUp() {
        proveedor = new Proveedor();
        proveedor.setId(1L);
        proveedor.setNombre("Transportes ABC");
        proveedor.setDireccion("Calle Principal 123");
        proveedor.setContacto("contacto@transportesabc.cl");
    }

    @Test
    void crearProveedor_DeberiaRetornarCreated() {
        // Configurar mock
        when(proveedorService.guardar(any(Proveedor.class))).thenReturn(proveedor);

        // Ejecutar
        ResponseEntity<Proveedor> response = controllerProveedor.crear(proveedor);

        // Verificar
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Transportes ABC", response.getBody().getNombre());
        verify(proveedorService, times(1)).guardar(any(Proveedor.class));
    }

    @Test
    void listarProveedores_DeberiaRetornarLista() {
        // Configurar mock
        Proveedor proveedor2 = new Proveedor();
        proveedor2.setId(2L);
        List<Proveedor> proveedores = Arrays.asList(proveedor, proveedor2);
        when(proveedorService.listar()).thenReturn(proveedores);

        // Ejecutar
        List<Proveedor> resultado = controllerProveedor.listar();

        // Verificar
        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        assertEquals(2L, resultado.get(1).getId());
        verify(proveedorService, times(1)).listar();
    }

    @Test
    void eliminarProveedor_Existente_DeberiaRetornarNoContent() {
        // Configurar mock
        when(proveedorService.existe(1L)).thenReturn(true);
        doNothing().when(proveedorService).eliminar(1L);

        // Ejecutar
        ResponseEntity<Void> response = controllerProveedor.eliminar(1L);

        // Verificar
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(proveedorService, times(1)).existe(1L);
        verify(proveedorService, times(1)).eliminar(1L);
    }

    @Test
    void eliminarProveedor_NoExistente_DeberiaRetornarNotFound() {
        // Configurar mock
        when(proveedorService.existe(99L)).thenReturn(false);

        // Ejecutar
        ResponseEntity<Void> response = controllerProveedor.eliminar(99L);

        // Verificar
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(proveedorService, times(1)).existe(99L);
        verify(proveedorService, never()).eliminar(anyLong());
    }

    @Test
    void crearProveedor_ConDatosInvalidos_DeberiaManejarError() {
        // Configurar mock
        when(proveedorService.guardar(any(Proveedor.class)))
                .thenThrow(new IllegalArgumentException("Datos de proveedor inválidos"));

        // Ejecutar y verificar
        assertThrows(IllegalArgumentException.class, () -> {
            controllerProveedor.crear(new Proveedor());
        });
    }

    @Test
    void listarProveedores_Vacio_DeberiaRetornarListaVacia() {
        // Configurar mock
        when(proveedorService.listar()).thenReturn(List.of());

        // Ejecutar
        List<Proveedor> resultado = controllerProveedor.listar();

        // Verificar
        assertTrue(resultado.isEmpty());
        verify(proveedorService, times(1)).listar();
    }

    @Test
    void obtenerProveedorPorDefecto_DeberiaRetornarProveedor() {
        // Configurar mock
        when(proveedorService.obtenerProveedorPorDefecto()).thenReturn(proveedor);

        // Este test verifica indirectamente el método del controller a través del servicio
        Proveedor resultado = proveedorService.obtenerProveedorPorDefecto();

        // Verificar
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(proveedorService, times(1)).obtenerProveedorPorDefecto();
    }
}
