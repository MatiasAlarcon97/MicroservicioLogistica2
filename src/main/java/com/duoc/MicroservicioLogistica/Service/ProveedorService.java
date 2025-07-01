package com.duoc.MicroservicioLogistica.Service;

import com.duoc.MicroservicioLogistica.Model.Proveedor;
import com.duoc.MicroservicioLogistica.Repository.RepositoryProveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {
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
    public boolean existe(Long id) {
        return repo.existsById(id);
    }
    public Proveedor obtenerProveedorPorDefecto() {
        return repo.findById(1L).orElseGet(() -> {
            Proveedor proveedor = new Proveedor();
            proveedor.setNombre("Proveedor por defecto");
            proveedor.setDireccion("Generica 123");
            proveedor.setContacto("defecto@proveedor.com");
            Proveedor guardado = repo.save(proveedor);
            System.out.println("Proveedor por defecto creado con ID: " + guardado.getId());
            return guardado;
        });
    }
}
