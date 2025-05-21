package com.salesianostriana.dam.GarciaMariaPablo.admin.daos.usuario.external;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDao_FiltrarIncidencia {

    private long id;
    private String nombre;
    private boolean selected;

    public static UsuarioDao_FiltrarIncidencia crearDao(Usuario usuario) {
        return UsuarioDao_FiltrarIncidencia.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre() + " " + usuario.getApellidos())
                .selected(false)
                .build();
    }

}
