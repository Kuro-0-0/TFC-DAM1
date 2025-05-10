package com.salesianostriana.dam.GarciaMariaPablo.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.repositorios.RepositorioUsuario;
import com.salesianostriana.dam.GarciaMariaPablo.servicios.base.ServicioBase;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuario extends ServicioBase<Usuario, Long, RepositorioUsuario> {

}
