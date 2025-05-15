package com.salesianostriana.dam.GarciaMariaPablo.old.daos.incidencia;

import com.salesianostriana.dam.GarciaMariaPablo.old.daos.estado.external.EstadoDao_EstadisticasIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.old.modelos.Incidencia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class IncidenciaDao_Estadisticas {

    private EstadoDao_EstadisticasIncidencia estado;

    public static IncidenciaDao_Estadisticas crearDao(Incidencia incidencia) {
        return IncidenciaDao_Estadisticas.builder()
                .estado(EstadoDao_EstadisticasIncidencia.crearDao(incidencia.getEstado()))
                .build();
    }

}


