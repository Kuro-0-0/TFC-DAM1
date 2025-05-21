package com.salesianostriana.dam.GarciaMariaPablo.global.daos.usuario;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor @Builder
public class UsuarioDao_Modificar {

    private long id;
    private String nombre, email, telefono,apellidos;

    public static UsuarioDao_Modificar crearDao(Usuario usuario) {
        return UsuarioDao_Modificar.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellidos(usuario.getApellidos())
                .telefono(usuario.getTelefono())
                .email(usuario.getEmail())
                .build();
    }
}
