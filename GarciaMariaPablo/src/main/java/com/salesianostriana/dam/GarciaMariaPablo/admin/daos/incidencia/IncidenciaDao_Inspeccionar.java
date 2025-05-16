package com.salesianostriana.dam.GarciaMariaPablo.admin.daos.incidencia;


import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.estado.external.EstadoDao_InspeccionarIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.usuario.external.UsuarioDao_InspeccionarIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Incidencia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncidenciaDao_Inspeccionar {
	
	private long id;
	private String titulo;
	private String ubicacion;
	private String descripcion;
	private UsuarioDao_InspeccionarIncidencia reportante;
	private EstadoDao_InspeccionarIncidencia estado;
	private UsuarioDao_InspeccionarIncidencia tecnico;
	private String fechaCreacion;
	
	public static IncidenciaDao_Inspeccionar crearDao(Incidencia incidencia) {
		return IncidenciaDao_Inspeccionar.builder()
				.id(incidencia.getId())
				.titulo(incidencia.getTitulo())
				.ubicacion(incidencia.getUbicacion())
				.descripcion(incidencia.getDescripcion())
				.reportante(UsuarioDao_InspeccionarIncidencia.crearDao(incidencia.getReportante()))
				.estado(EstadoDao_InspeccionarIncidencia.crearDao(incidencia.getEstado()))
				.tecnico(UsuarioDao_InspeccionarIncidencia.crearDao(incidencia.getTecnico()))
				.fechaCreacion(incidencia.getFechaCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
				.build();
	}
	
	

}
