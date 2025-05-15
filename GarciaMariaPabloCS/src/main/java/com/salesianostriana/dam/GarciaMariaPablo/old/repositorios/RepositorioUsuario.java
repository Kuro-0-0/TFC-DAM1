package com.salesianostriana.dam.GarciaMariaPablo.old.repositorios;

import com.salesianostriana.dam.GarciaMariaPablo.old.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.old.modelos.utilidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

    List<Usuario> findByRol(Rol rol);

    Usuario findByUsername(String s);

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
                                @Param("roles") List<Long> roles);

    @Query("SELECT u.password FROM Usuario u WHERE u.id = :id")
    Optional<String> findPasswordById(@Param("id") long id);
}
