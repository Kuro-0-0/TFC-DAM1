package com.salesianostriana.dam.GarciaMariaPablo.admin.daos.estado.external;


import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EstadoDao_FiltrarIncidencia {

    private String nombre;
    private String valor;
    private boolean selected;

    public static EstadoDao_FiltrarIncidencia crearDao(Estado estado) {
        return EstadoDao_FiltrarIncidencia.builder()
                .nombre(estado.getNombre())
                .valor(estado.getValor())
                .selected(false)
                .build();
    }
}
