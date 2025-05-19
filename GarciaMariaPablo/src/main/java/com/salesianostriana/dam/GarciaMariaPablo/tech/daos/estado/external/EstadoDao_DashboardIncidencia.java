package com.salesianostriana.dam.GarciaMariaPablo.tech.daos.estado.external;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class EstadoDao_DashboardIncidencia {

    private String nombre;
    private String colorFondo;
    private String colorTexto;

    public static EstadoDao_DashboardIncidencia crearDao(Estado estado) {
        return EstadoDao_DashboardIncidencia.builder()
                .nombre(estado.getNombre())
                .colorFondo(estado.getColorFondo())
                .colorTexto(estado.getColorTexto())
                .build();
    }
}