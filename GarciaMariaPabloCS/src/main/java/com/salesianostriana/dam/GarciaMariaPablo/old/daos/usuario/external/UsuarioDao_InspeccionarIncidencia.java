package com.salesianostriana.dam.GarciaMariaPablo.old.daos.usuario.external;


import com.salesianostriana.dam.GarciaMariaPablo.old.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.old.modelos.utilidades.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class UsuarioDao_InspeccionarIncidencia {
	
	private String nombre;
	private Rol rol;
	
	
	public static UsuarioDao_InspeccionarIncidencia crearDao(Usuario reportante) {
		return UsuarioDao_InspeccionarIncidencia.builder()
				.nombre(reportante.getNombre() + " " + reportante.getApellidos())
				.rol(reportante.getRol())
				.build();
	}

}
