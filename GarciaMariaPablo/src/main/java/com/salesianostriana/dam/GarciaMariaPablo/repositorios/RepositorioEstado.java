package com.salesianostriana.dam.GarciaMariaPablo.repositorios;

import com.salesianostriana.dam.GarciaMariaPablo.modelos.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioEstado extends JpaRepository<Estado, Long> {

    List<Estado> findByActivo(boolean activo);

    Optional<Estado> findByValor(String estado);
}
