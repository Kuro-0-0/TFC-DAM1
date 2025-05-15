package com.salesianostriana.dam.GarciaMariaPablo.old.daos.estado.external;


import com.salesianostriana.dam.GarciaMariaPablo.old.modelos.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class EstadoDao_ModificarIncidencia {
	
	private String nombre;
	private String valor;
	
	public static EstadoDao_ModificarIncidencia crearDao(Estado estado) {
		return EstadoDao_ModificarIncidencia.builder()
				.nombre(estado.getNombre())
				.valor(estado.getValor())
				.build();
	}

}
