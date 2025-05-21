package com.salesianostriana.dam.GarciaMariaPablo.global.repositorios;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioEstado extends JpaRepository<Estado, Long> {

    List<Estado> findByActivo(boolean activo);

    Optional<Estado> findByValor(String estado);

    @Query("SELECT e FROM Estado e " +
            "WHERE (:buscar IS NULL OR LOWER(e.nombre) like LOWER(CONCAT('%',:buscar,'%')))" +
            "OR (:buscar IS NULL OR LOWER(e.valor) like LOWER(CONCAT('%',:buscar,'%')))")
    List<Estado> findByText(@Param("buscar") String buscar);

	List<Estado> findByNombre(String nombre);
}
