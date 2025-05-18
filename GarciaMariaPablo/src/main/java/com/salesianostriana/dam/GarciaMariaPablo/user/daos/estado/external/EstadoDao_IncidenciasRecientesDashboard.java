package com.salesianostriana.dam.GarciaMariaPablo.user.daos.estado.external;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class EstadoDao_IncidenciasRecientesDashboard {

    private String nombre, colorTexto,colorFondo;

    public static EstadoDao_IncidenciasRecientesDashboard crearDao(Estado estado) {
        return  EstadoDao_IncidenciasRecientesDashboard.builder()
                .nombre(estado.getNombre())
                .colorTexto(estado.getColorTexto())
                .colorFondo(estado.getColorFondo())
                .build();
    }
}
