package com.salesianostriana.dam.GarciaMariaPablo.global.repositorios;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

}
