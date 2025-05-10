package com.salesianostriana.dam.GarciaMariaPablo.repositorios;

import com.salesianostriana.dam.GarciaMariaPablo.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.modelos.utilidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

    List<Usuario> findByRol(Rol rol);

}
