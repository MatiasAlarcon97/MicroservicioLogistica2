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
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String direccion_entrega;
    private LocalDate fecha_envio;

    @ManyToOne
    @JoinColumn(name = "proveedor_id") // FK a proveedor
    private Proveedor proveedor;
}
