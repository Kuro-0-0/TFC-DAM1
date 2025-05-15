package com.salesianostriana.dam.GarciaMariaPablo.global.daos.usuario;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor @Builder
public class UsuarioDao_LogIn {

    private String username, password;

    public static UsuarioDao_LogIn crearDao(Usuario usuario) {
        return UsuarioDao_LogIn.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .build();
    }
}
