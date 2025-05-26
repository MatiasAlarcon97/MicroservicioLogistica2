package com.duoc.MicroservicioLogistica.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha_creacion;

    private String estado;

    @ManyToOne
    @JoinColumn(name = "envio_id") // FK a envio
    private Envio envio;
}
