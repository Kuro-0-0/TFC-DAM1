package com.salesianostriana.dam.GarciaMariaPablo.global.repositorios;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioIncidencia extends JpaRepository<Incidencia, Long> {

    @Query("SELECT i " +
            "FROM Incidencia i " +
            "WHERE (:reportantes IS NULL OR i.reportante.id IN :reportantes)" +
            "AND (:estados IS NULL OR i.estado.valor IN :estados)" +
            "AND (:titulo IS NULL OR LOWER(i.titulo) like lower(CONCAT('%',:titulo,'%')) )" +
            "AND (:ubicacion IS NULL OR lower(i.ubicacion) like lower(CONCAT('%',:ubicacion,'%')) )" +
            "AND (:desactivados = true OR i.estado.activo = true)"
    )
    List<Incidencia> listFilters(@Param("reportantes") List<Long> reportantes,
                                 @Param("estados") List<String> estados,
                                 @Param("titulo")String filtroTitulo,
                                 @Param("ubicacion")String filtroUbicacion,
                                 @Param("desactivados")Boolean mostrarDesactivados);

    @Query("""
    SELECT i FROM Incidencia  i
        WHERE i.reportante.id = :idUsuario""")
    List<Incidencia> getIncidenciasPorIdReportante(@Param("idUsuario") Long idUsuario);
}
