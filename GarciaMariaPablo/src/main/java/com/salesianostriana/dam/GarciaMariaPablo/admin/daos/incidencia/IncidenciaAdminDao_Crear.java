package com.salesianostriana.dam.GarciaMariaPablo.admin.daos.incidencia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncidenciaAdminDao_Crear {
	
	private String titulo;
	private String ubicacion;
	private String descripcion;
	private Long reportante;
	private Long tecnico;
	private String estado; // valor Estado
	private LocalDateTime fechaCreacion;
	
}
