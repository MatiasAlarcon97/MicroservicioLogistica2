package com.duoc.MicroservicioLogistica.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String direccion;

    private String contacto;
}
