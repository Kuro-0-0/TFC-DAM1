package com.salesianostriana.dam.GarciaMariaPablo.admin.daos.usuario;


import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.RolesUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDao_Crear {

    private String username;
    private String password;
    private String nombre;
    private String apellidos;

    private RolesUsuario rol;

    public static UsuarioDao_Crear crearDao(Usuario usuario) {
        return UsuarioDao_Crear.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .nombre(usuario.getNombre())
                .apellidos(usuario.getApellidos())
                .rol(usuario.getRol())
                .build();
    }

    public Usuario revertirDao() {
        return Usuario.builder()
                .username(this.username)
                .password(this.password)
                .nombre(this.nombre)
                .apellidos(this.apellidos)
                .rol(this.rol)
                .editable(true)
                .build();
    }

}
