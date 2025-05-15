package com.salesianostriana.dam.GarciaMariaPablo.old.daos.usuario;

import com.salesianostriana.dam.GarciaMariaPablo.old.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.old.modelos.utilidades.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDao_Modificar {

    private long id;
    private String username;
    private String password;
    private String nombre;
    private String apellidos;

    private Rol rol;

    public static UsuarioDao_Modificar crearDao(Usuario usuario) {
        return UsuarioDao_Modificar.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .password("")
                .nombre(usuario.getNombre())
                .apellidos(usuario.getApellidos())
                .rol(usuario .getRol())
                .build();
    }

}
