package com.salesianostriana.dam.GarciaMariaPablo.tech.daos.usuario.external;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class UsuarioTechDao_ListarIncidencia {
    private long id;
    private String nombre;
    private String apellidos;

    public static UsuarioTechDao_ListarIncidencia crearDao(Usuario usuario) {
        return UsuarioTechDao_ListarIncidencia.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellidos(usuario.getApellidos())
                .build();
    }
}
