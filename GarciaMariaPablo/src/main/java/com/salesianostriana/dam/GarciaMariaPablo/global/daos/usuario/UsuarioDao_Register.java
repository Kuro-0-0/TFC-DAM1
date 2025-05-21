package com.salesianostriana.dam.GarciaMariaPablo.global.daos.usuario;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.RolesUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor @Builder
public class UsuarioDao_Register {


    private String username, password,password2, nombre, apellidos;

    public static UsuarioDao_Register crearDao(Usuario usuario) {
        return UsuarioDao_Register.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .password2(usuario.getPassword())
                .nombre(usuario.getNombre())
                .apellidos(usuario.getApellidos())
                .build();
    }

    public Usuario revertirDao() {
        return Usuario.builder()
                .username(username)
                .password(password)
                .nombre(nombre)
                .apellidos(apellidos)
                .rol(RolesUsuario.valueOf("USER"))
                .build();
    }
}
