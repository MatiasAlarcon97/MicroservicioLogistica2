package com.duoc.MicroservicioLogistica.Service;

import com.duoc.MicroservicioLogistica.Model.Pedido;
import com.duoc.MicroservicioLogistica.Repository.RepositoryPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicePedido {
    @Autowired
    private RepositoryPedido repo;

    public Pedido guardar(Pedido p) {
        return repo.save(p);
    }

    public List<Pedido> listar() {
        return repo.findAll();
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
