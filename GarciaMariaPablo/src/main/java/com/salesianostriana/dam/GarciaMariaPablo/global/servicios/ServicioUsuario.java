package com.salesianostriana.dam.GarciaMariaPablo.global.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.repositorios.RepositorioUsuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.seguridad.CodificadorContrasenas;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.otros.base.ServicioBaseImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ServicioUsuario extends ServicioBaseImpl<Usuario,Long, RepositorioUsuario> {


    public Optional<Usuario> findByUsername(String username) {
        return repositorio.findByUsername(username);
    }

    public Usuario getSinTecnico() {
        return repositorio.findByUsername("sin-tecnico").orElseThrow();
    }

    public Usuario crearUsuario(Usuario usuario) {
        usuario.setFechaRegistro(LocalDate.now());
        usuario.setPassword(CodificadorContrasenas.passwordEncoder().encode(usuario.getPassword()));
        return usuario;
    }

    public Usuario findByEmail(String email) {
        return repositorio.findByEmail(email);
    }
}
