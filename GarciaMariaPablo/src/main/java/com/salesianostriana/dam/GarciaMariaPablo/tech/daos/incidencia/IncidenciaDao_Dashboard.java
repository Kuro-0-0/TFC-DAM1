package com.salesianostriana.dam.GarciaMariaPablo.tech.daos.incidencia;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Incidencia;
import com.salesianostriana.dam.GarciaMariaPablo.tech.daos.estado.external.EstadoDao_DashboardIncidencia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
@NoArgsConstructor @Builder
public class IncidenciaDao_Dashboard {

    private long id;
    private String titulo;
    private LocalDateTime fechaCreacion;
    private EstadoDao_DashboardIncidencia estado;

    public static IncidenciaDao_Dashboard crearDao(Incidencia incidencia) {
        return IncidenciaDao_Dashboard.builder()
                .id(incidencia.getId())
                .titulo(incidencia.getTitulo())
                .fechaCreacion(incidencia.getFechaCreacion())
                .estado(EstadoDao_DashboardIncidencia.crearDao(incidencia.getEstado()))
                .build();
    }
}
