package com.salesianostriana.dam.GarciaMariaPablo.global.daos.incidencia;


import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.estado.external.EstadoDao_ListarIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.usuario.external.UsuarioDao_ListarIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Incidencia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidenciaDao_Listar {

    private long id;
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private UsuarioDao_ListarIncidencia reportante;
    private EstadoDao_ListarIncidencia estado;
    private String fechaCreacion;
    private LocalDateTime fechaModificacion;
    private LocalDateTime fecha;

    public static IncidenciaDao_Listar crearDao(Incidencia incidencia) {
        return IncidenciaDao_Listar.builder()
                .id(incidencia.getId())
                .titulo(incidencia.getTitulo())
                .descripcion(incidencia.getDescripcion())
                .ubicacion(incidencia.getUbicacion())
                .reportante(UsuarioDao_ListarIncidencia.crearDao(incidencia.getReportante()))
                .estado(EstadoDao_ListarIncidencia.crearDao(incidencia.getEstado()))
                .fechaCreacion(incidencia.getFechaCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .fechaModificacion(incidencia.getFechaModificacion())
                .build();
    }

}
