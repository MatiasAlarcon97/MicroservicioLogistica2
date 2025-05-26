package com.duoc.MicroservicioLogistica.Repository;

import com.duoc.MicroservicioLogistica.Model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryEnvio extends JpaRepository<Envio, Long> {
}
