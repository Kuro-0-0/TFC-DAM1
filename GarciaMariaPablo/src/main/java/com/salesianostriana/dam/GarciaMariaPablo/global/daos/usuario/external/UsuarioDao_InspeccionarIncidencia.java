package com.salesianostriana.dam.GarciaMariaPablo.global.daos.usuario.external;


import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.RolesUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDao_InspeccionarIncidencia {
	
	private String nombre;
	private RolesUsuario rol;
	
	
	public static UsuarioDao_InspeccionarIncidencia crearDao(Usuario reportante) {
		return UsuarioDao_InspeccionarIncidencia.builder()
				.nombre(reportante.getNombre() + " " + reportante.getApellidos())
				.rol(reportante.getRol())
				.build();
	}

}
