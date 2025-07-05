package com.duoc.MicroservicioLogistica.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PedidoConEnvioDTO {
    private Long idVenta;
    private String direccion_entrega;
    private LocalDate fecha_envio;
    private String estado_pedido;
}
