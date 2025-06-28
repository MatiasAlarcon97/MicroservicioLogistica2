package com.duoc.MicroservicioLogistica.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha_creacion;

    private String estado;
    private Long id_venta;

    @ManyToOne
    @JoinColumn(name = "envio_id") // FK a envio
    private Envio envio;
}
