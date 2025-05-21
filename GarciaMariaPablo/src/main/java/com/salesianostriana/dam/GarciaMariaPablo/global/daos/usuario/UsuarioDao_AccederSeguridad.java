package com.salesianostriana.dam.GarciaMariaPablo.global.daos.usuario;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.RolesUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor
@AllArgsConstructor
public class UsuarioDao_AccederSeguridad {

    private String username;
    private RolesUsuario rol;

    public static UsuarioDao_AccederSeguridad crearDao(Usuario usuario) {
        return UsuarioDao_AccederSeguridad.builder()
                .username(usuario.getUsername())
                .rol(usuario.getRol())
                .build();
    }
}
