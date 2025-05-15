package com.salesianostriana.dam.GarciaMariaPablo.global.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.repositorios.RepositorioUsuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.seguridad.CodificadorContrasenas;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.otros.base.ServicioBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicioUsuario extends ServicioBaseImpl<Usuario,Long, RepositorioUsuario> {

    @Autowired
    private CodificadorContrasenas codificadorContrasenas;

    public Optional<Usuario> findByUsername(String username) {
        return repositorio.findByUsername(username);
    }

    public Usuario crearUsuario(Usuario usuario) {
        usuario.setPassword(codificadorContrasenas.passwordEncoder().encode(usuario.getPassword()));
        return usuario;
    }
}
