package com.salesianostriana.dam.GarciaMariaPablo.admin.daos.usuario.external;


import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.RolesUsuario;
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
    private RolesUsuario rol;

    public static UsuarioDao_ListarIncidencia crearDao(Usuario usuario) {
        return UsuarioDao_ListarIncidencia.builder()
                .nombre(usuario.getNombre() + " " + usuario.getApellidos())
                .rol(usuario.getRol())
                .build();
    }

}
