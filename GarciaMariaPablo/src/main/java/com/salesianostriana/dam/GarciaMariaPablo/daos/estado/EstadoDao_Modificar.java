package com.salesianostriana.dam.GarciaMariaPablo.daos.estado;

import com.salesianostriana.dam.GarciaMariaPablo.modelos.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @AllArgsConstructor
@NoArgsConstructor @Data
public class EstadoDao_Modificar {

    private long id;
    private String nombre;
    private boolean activo;
    private String colorTexto;
    private String colorFondo;

    public static EstadoDao_Modificar crearDao(Estado estado) {
        return EstadoDao_Modificar.builder()
                .id(estado.getId())
                .nombre(estado.getNombre())
                .activo(estado.isActivo())
                .colorFondo(estado.getColorFondo())
                .colorTexto(estado.getColorTexto())
                .build();
    }



}
