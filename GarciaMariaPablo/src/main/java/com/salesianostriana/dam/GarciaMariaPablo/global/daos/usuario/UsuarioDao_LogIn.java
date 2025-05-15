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

    /**
     *
     * Helper cuya funcion consiste en crear un Dao a partir de un Objeto base.
     *
     * @param usuario Usuario a partir del cual se creara el Dao.
     * @return El Dao Creado
     */
    public static UsuarioDao_LogIn crearDao(Usuario usuario) {
        return UsuarioDao_LogIn.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .build();
    }
}
