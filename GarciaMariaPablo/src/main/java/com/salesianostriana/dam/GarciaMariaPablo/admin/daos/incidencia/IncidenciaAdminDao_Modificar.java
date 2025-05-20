package com.salesianostriana.dam.GarciaMariaPablo.admin.daos.incidencia;


import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.estado.external.EstadoDao_ModificarIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.usuario.external.UsuarioDao_FormularioIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Incidencia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncidenciaAdminDao_Modificar {
	
	private long id;
	private String titulo;
	private String ubicacion;
	private String descripcion;
	private UsuarioDao_FormularioIncidencia reportante;
	private UsuarioDao_FormularioIncidencia tecnico;
	private EstadoDao_ModificarIncidencia estado;
	private LocalDateTime fechaCreacion;
	private  LocalDateTime fechaModificacion;
	private  LocalDateTime fechaIEA;

	public static IncidenciaAdminDao_Modificar crearDao(Incidencia incidencia) {
		return IncidenciaAdminDao_Modificar.builder()
				.id(incidencia.getId())
				.titulo(incidencia.getTitulo())
				.ubicacion(incidencia.getUbicacion())
				.descripcion(incidencia.getDescripcion())
				.reportante(UsuarioDao_FormularioIncidencia.crearDao(incidencia.getReportante()))
				.tecnico(UsuarioDao_FormularioIncidencia.crearDao(incidencia.getTecnico()))
				.estado(EstadoDao_ModificarIncidencia.crearDao(incidencia.getEstado()))
				.fechaCreacion(incidencia.getFechaIEA())
				.fechaModificacion(incidencia.getFechaModificacion())
				.fechaIEA(incidencia.getFechaIEA())
				.build();
	}
}
