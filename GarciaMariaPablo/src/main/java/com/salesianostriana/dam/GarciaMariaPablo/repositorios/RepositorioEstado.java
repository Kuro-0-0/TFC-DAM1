package com.salesianostriana.dam.GarciaMariaPablo.repositorios;

import com.salesianostriana.dam.GarciaMariaPablo.modelos.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioEstado extends JpaRepository<Estado, Long> {

    List<Estado> findByActivo(boolean activo);
}
