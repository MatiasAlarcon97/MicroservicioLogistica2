package com.duoc.MicroservicioLogistica.Repository;

import com.duoc.MicroservicioLogistica.Model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryPedido extends JpaRepository<Pedido, Long> {
}
