package com.duoc.MicroservicioLogistica.Service;

import com.duoc.MicroservicioLogistica.Model.Pedido;
import com.duoc.MicroservicioLogistica.Repository.RepositoryPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private  RepositoryPedido repo;

    public PedidoService(RepositoryPedido repo) {
        this.repo = repo;
    }

    public Pedido crear(Pedido pedido) {
        return repo.save(pedido);
    }

    public Pedido guardar(Pedido p) {
        return repo.save(p);
    }

    public List<Pedido> listar() {
        return repo.findAll();
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public List<Pedido> listarTodos() {
        return repo.findAll();
    }

    public boolean existe(Long id) {
        return repo.existsById(id);
    }
}
