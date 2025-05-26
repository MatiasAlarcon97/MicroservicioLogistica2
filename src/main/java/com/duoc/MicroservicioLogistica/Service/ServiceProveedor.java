package com.duoc.MicroservicioLogistica.Service;

import com.duoc.MicroservicioLogistica.Model.Proveedor;
import com.duoc.MicroservicioLogistica.Repository.RepositoryProveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceProveedor {
    @Autowired
    private RepositoryProveedor repo;

    public Proveedor guardar(Proveedor p) {
        return repo.save(p);
    }

    public List<Proveedor> listar() {
        return repo.findAll();
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
