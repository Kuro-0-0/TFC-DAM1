package com.salesianostriana.dam.GarciaMariaPablo.user.daos.incidencia.external;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Incidencia;
import com.salesianostriana.dam.GarciaMariaPablo.user.daos.estado.external.EstadoDao_IncidenciasRecientesDashboard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class IncidenciaDao_RecientesDashboard {

    private String titulo;
    private EstadoDao_IncidenciasRecientesDashboard estado;

    public static IncidenciaDao_RecientesDashboard crearDao(Incidencia incidencia) {
        return IncidenciaDao_RecientesDashboard.builder()
                .titulo(incidencia.getTitulo())
                .estado(EstadoDao_IncidenciasRecientesDashboard.crearDao(incidencia.getEstado()))
                .build();
    }


}
