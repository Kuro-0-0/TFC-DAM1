package com.salesianostriana.dam.GarciaMariaPablo.global.repositorios;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.RolesUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    @Query("SELECT u FROM Usuario u " +
            "WHERE (:username IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%',:username,'%'))) " +
            "AND (:nombre IS NULL OR LOWER(u.nombre) LIKE LOWER(CONCAT('%',:nombre,'%'))) " +
            "AND (:apellidos IS NULL OR LOWER(u.apellidos) LIKE LOWER(CONCAT('%',:apellidos,'%'))) " +
            "AND (:ocultos = TRUE OR u.editable = TRUE ) " +
            "AND (:roles IS NULL OR u.rol IN :roles)")
    List<Usuario> findByFilters(@Param("username") String filtroUsername,
                                @Param("nombre") String filtroNombre,
                                @Param("apellidos") String filtroApellidos,
                                @Param("ocultos") boolean ocultos,
                                @Param("roles") List<String> roles);

    @Query("SELECT u.password FROM Usuario u WHERE u.id = :id")
    Optional<String> findPasswordById(@Param("id") long id);
    List<Usuario> findByRol(RolesUsuario rol);
}
