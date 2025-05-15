package com.salesianostriana.dam.GarciaMariaPablo.old.daos.usuario;

import com.salesianostriana.dam.GarciaMariaPablo.old.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.old.modelos.utilidades.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UsuarioDao_Listar {

    private long id;
    private String username;
    private String nombre;
    private String apellidos;
    private boolean editable;

    private Rol rol;

    public static UsuarioDao_Listar crearDao(Usuario usuario) {
        return UsuarioDao_Listar.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .nombre(usuario.getNombre())
                .apellidos(usuario.getApellidos())
                .rol(usuario.getRol())
                .editable(usuario.isEditable())
                .build();
    }

}
