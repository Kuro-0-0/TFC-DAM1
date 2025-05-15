package com.salesianostriana.dam.GarciaMariaPablo.old.daos.estado.external;

import com.salesianostriana.dam.GarciaMariaPablo.old.modelos.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class EstadoDao_InspeccionarIncidencia {

	private String nombre;
	private String colorTexto;
	private String colorFondo;
	
	public static EstadoDao_InspeccionarIncidencia crearDao(Estado estado) {
		return EstadoDao_InspeccionarIncidencia.builder()
				.nombre(estado.getNombre())
				.colorFondo(estado.getColorFondo())
				.colorTexto(estado.getColorTexto())
				.build();
	}
	
}
