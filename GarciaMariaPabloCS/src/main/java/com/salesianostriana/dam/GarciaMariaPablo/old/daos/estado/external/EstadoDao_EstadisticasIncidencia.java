package com.salesianostriana.dam.GarciaMariaPablo.old.daos.estado.external;

import com.salesianostriana.dam.GarciaMariaPablo.old.modelos.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class EstadoDao_EstadisticasIncidencia {
    private String nombre;
    private String colorFondo;
    private String colorTexto;

    public static EstadoDao_EstadisticasIncidencia crearDao(Estado estado) {
        return EstadoDao_EstadisticasIncidencia.builder()
                .nombre(estado.getNombre())
                .colorFondo(estado.getColorFondo())
                .colorTexto(estado.getColorTexto())
                .build();
    }

}
