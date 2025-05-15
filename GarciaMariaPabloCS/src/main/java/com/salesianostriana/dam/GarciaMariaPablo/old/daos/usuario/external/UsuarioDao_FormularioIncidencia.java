package com.salesianostriana.dam.GarciaMariaPablo.old.daos.usuario.external;


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
public class UsuarioDao_FormularioIncidencia {

	private long id;
	private String nombre;
	private Rol rol;
	
	public static UsuarioDao_FormularioIncidencia crearDao(Usuario usuario) {
		return UsuarioDao_FormularioIncidencia.builder()
				.id(usuario.getId())
				.nombre(usuario.getNombre() + " " + usuario.getApellidos())
				.rol(usuario.getRol())
				.build();
	}

}
