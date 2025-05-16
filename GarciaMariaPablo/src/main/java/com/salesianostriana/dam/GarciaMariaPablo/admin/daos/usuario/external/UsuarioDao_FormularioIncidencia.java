package com.salesianostriana.dam.GarciaMariaPablo.admin.daos.usuario.external;


import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.RolesUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDao_FormularioIncidencia {

	private long id;
	private String nombre;
	private RolesUsuario rol;
	
	public static UsuarioDao_FormularioIncidencia crearDao(Usuario usuario) {
		return UsuarioDao_FormularioIncidencia.builder()
				.id(usuario.getId())
				.nombre(usuario.getNombre() + " " + usuario.getApellidos())
				.rol(usuario.getRol())
				.build();
	}

}
