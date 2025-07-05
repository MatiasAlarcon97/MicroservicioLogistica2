package com.duoc.MicroservicioLogistica.MicroservicioLogistica.Controller;



import com.duoc.MicroservicioLogistica.Controller.ControllerEnvio;
import com.duoc.MicroservicioLogistica.Model.Envio;
import com.duoc.MicroservicioLogistica.Model.Proveedor;
import com.duoc.MicroservicioLogistica.Service.EnvioService;
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
public class ControllerEnvioTest {

    @Mock
    private EnvioService envioService;

    @InjectMocks
    private ControllerEnvio controllerEnvio;

    private Envio envio;
    private Proveedor proveedor;

    @BeforeEach
    void setUp() {
        proveedor = new Proveedor();
        proveedor.setId(1L);
        proveedor.setNombre("Transportes ABC");

        envio = new Envio();
        envio.setId(1L);
        envio.setDireccion_entrega("Calle Principal 123");
        envio.setFecha_envio(LocalDate.now().plusDays(2));
        envio.setProveedor(proveedor);
    }

    @Test
    void crearEnvio_DeberiaRetornarCreated() {
        // Configurar mock
        when(envioService.guardar(any(Envio.class))).thenReturn(envio);

        // Ejecutar
        ResponseEntity<Envio> response = controllerEnvio.crear(envio);

        // Verificar
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Calle Principal 123", response.getBody().getDireccion_entrega());
        verify(envioService, times(1)).guardar(any(Envio.class));
    }

    @Test
    void listarEnvios_DeberiaRetornarLista() {
        // Configurar mock
        Envio envio2 = new Envio();
        envio2.setId(2L);
        List<Envio> envios = Arrays.asList(envio, envio2);
        when(envioService.listar()).thenReturn(envios);

        // Ejecutar
        List<Envio> resultado = controllerEnvio.listar();

        // Verificar
        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        assertEquals(2L, resultado.get(1).getId());
        verify(envioService, times(1)).listar();
    }

    @Test
    void eliminarEnvio_Existente_DeberiaRetornarNoContent() {
        // Configurar mock
        when(envioService.existe(1L)).thenReturn(true);
        doNothing().when(envioService).eliminar(1L);

        // Ejecutar
        ResponseEntity<Void> response = controllerEnvio.eliminar(1L);

        // Verificar
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(envioService, times(1)).existe(1L);
        verify(envioService, times(1)).eliminar(1L);
    }

    @Test
    void eliminarEnvio_NoExistente_DeberiaRetornarNotFound() {
        // Configurar mock
        when(envioService.existe(99L)).thenReturn(false);

        // Ejecutar
        ResponseEntity<Void> response = controllerEnvio.eliminar(99L);

        // Verificar
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(envioService, times(1)).existe(99L);
        verify(envioService, never()).eliminar(anyLong());
    }

    @Test
    void crearEnvio_ConDatosInvalidos_DeberiaManejarError() {
        // Configurar mock
        when(envioService.guardar(any(Envio.class)))
                .thenThrow(new IllegalArgumentException("Datos de envío inválidos"));

        // Ejecutar y verificar
        assertThrows(IllegalArgumentException.class, () -> {
            controllerEnvio.crear(new Envio());
        });
    }
}
