package com.salesianostriana.dam.GarciaMariaPablo.daos.incidencia;

import com.salesianostriana.dam.GarciaMariaPablo.daos.estado.external.EstadoDao_ModificarIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.daos.usuario.external.UsuarioDao_FormularioIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.modelos.Incidencia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncidenciaDao_Modificar {
	
	private long id;
	private String titulo;
	private String ubicacion;
	private String descripcion;
	private UsuarioDao_FormularioIncidencia reportante;
	private UsuarioDao_FormularioIncidencia tecnico;
	private EstadoDao_ModificarIncidencia estado;
	private LocalDate fechaCreacion;
	
	public static IncidenciaDao_Modificar crearDao(Incidencia incidencia) {
		return IncidenciaDao_Modificar.builder()
				.id(incidencia.getId())
				.titulo(incidencia.getTitulo())
				.ubicacion(incidencia.getUbicacion())
				.descripcion(incidencia.getDescripcion())
				.reportante(UsuarioDao_FormularioIncidencia.crearDao(incidencia.getReportante()))
				.tecnico(UsuarioDao_FormularioIncidencia.crearDao(incidencia.getTecnico()))
				.estado(EstadoDao_ModificarIncidencia.crearDao(incidencia.getEstado()))
				.fechaCreacion(incidencia.getFechaCreacion())
				.build();
	}
}
