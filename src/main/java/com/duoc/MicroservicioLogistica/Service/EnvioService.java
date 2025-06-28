package com.duoc.MicroservicioLogistica.Service;

import com.duoc.MicroservicioLogistica.Model.Envio;
import com.duoc.MicroservicioLogistica.Repository.RepositoryEnvio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvioService {
    @Autowired
    private RepositoryEnvio repo;

    public Envio guardar(Envio e) {
        return repo.save(e);
    }

    public List<Envio> listar() {
        return repo.findAll();
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
    public boolean existe(Long id) {
        return repo.existsById(id);
    }
}
