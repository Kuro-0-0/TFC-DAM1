package com.salesianostriana.dam.GarciaMariaPablo.tech.daos.otros;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor @Builder
public class EstadisticasDao_Dashboard {

    private int incidenciasResueltas, incidenciasAsignadas,incidenciasSinAsignar;

    public static EstadisticasDao_Dashboard crearDao(int[] i) {
        return EstadisticasDao_Dashboard.builder()
                .incidenciasResueltas(i[0])
                .incidenciasAsignadas(i[1])
                .incidenciasSinAsignar(i[2])
                .build();
    }
}
