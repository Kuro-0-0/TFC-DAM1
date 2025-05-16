package com.salesianostriana.dam.GarciaMariaPablo.admin.daos.estado.external;


import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoDao_Seleccionar {
	
	private String nombre;
	private String valor;
	private String colorFondo;
	
	public static EstadoDao_Seleccionar crearDao(Estado estado) {
		return EstadoDao_Seleccionar.builder()
				.nombre(estado.getNombre())
				.valor(estado.getValor())
				.colorFondo(estado.getColorFondo())
				.build();
	}

}
