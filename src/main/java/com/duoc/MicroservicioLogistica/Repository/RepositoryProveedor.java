package com.duoc.MicroservicioLogistica.Repository;

import com.duoc.MicroservicioLogistica.Model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryProveedor extends JpaRepository<Proveedor, Long> {
}
