package com.salesianostriana.dam.GarciaMariaPablo.admin.daos.estado.external;


import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class EstadoDao_ListarIncidencia {

    private String nombre;
    private String colorTexto;
    private String colorFondo;

    public static EstadoDao_ListarIncidencia crearDao(Estado estado) {
        return EstadoDao_ListarIncidencia.builder()
                .nombre(estado.getNombre())
                .colorFondo(estado.getColorFondo())
                .colorTexto(estado.getColorTexto())
                .build();
    }

}
