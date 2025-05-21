package com.salesianostriana.dam.GarciaMariaPablo.tech.daos.otros;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor @Builder
public class EstadisticasDao_Listar {

    private int totalIncidencias, incidenciasEnProgreso,incidenciasResueltas;

    public static EstadisticasDao_Listar crearDao(int[] i) {
        return EstadisticasDao_Listar.builder()
                .totalIncidencias(i[0])
                .incidenciasEnProgreso(i[1])
                .incidenciasResueltas(i[2])
                .build();
    }
}
