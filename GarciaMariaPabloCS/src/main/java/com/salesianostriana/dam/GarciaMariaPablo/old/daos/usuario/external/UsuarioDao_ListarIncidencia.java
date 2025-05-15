package com.salesianostriana.dam.GarciaMariaPablo.old.daos.usuario.external;

import com.salesianostriana.dam.GarciaMariaPablo.old.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.old.modelos.utilidades.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class UsuarioDao_ListarIncidencia {

    private String nombre;
    private Rol rol;

    public static UsuarioDao_ListarIncidencia crearDao(Usuario usuario) {
        return UsuarioDao_ListarIncidencia.builder()
                .nombre(usuario.getNombre() + " " + usuario.getApellidos())
                .rol(usuario.getRol())
                .build();
    }

}
