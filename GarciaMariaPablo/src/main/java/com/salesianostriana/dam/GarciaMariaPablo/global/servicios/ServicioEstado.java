package com.salesianostriana.dam.GarciaMariaPablo.global.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Estado;
import com.salesianostriana.dam.GarciaMariaPablo.global.repositorios.RepositorioEstado;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.otros.base.ServicioBaseImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicioEstado extends ServicioBaseImpl<Estado,Long, RepositorioEstado> {

    public Estado getSinEstado() {
        return repositorio.findByValor("sin-estado").orElseThrow();
    }

    public Optional<Estado> findByValor(String valor) {
        return repositorio.findByValor(valor);
    }
}
