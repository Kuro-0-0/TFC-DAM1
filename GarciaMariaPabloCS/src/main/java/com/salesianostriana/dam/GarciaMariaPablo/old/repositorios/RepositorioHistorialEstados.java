package com.salesianostriana.dam.GarciaMariaPablo.old.repositorios;

import com.salesianostriana.dam.GarciaMariaPablo.old.modelos.HistorialEstados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioHistorialEstados extends JpaRepository<HistorialEstados, Long> {
}
