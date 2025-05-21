package com.salesianostriana.dam.GarciaMariaPablo.tech.daos.estado.external;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor
@AllArgsConstructor
public class EstadoTechDao_ListarIncidencia {

    private String colorFondo;
    private String colorTexto;
    private String nombre;
    private String tipo;
    private String valor;

    public static EstadoTechDao_ListarIncidencia crearDao(Estado estado) {
        return EstadoTechDao_ListarIncidencia.builder()
                .colorFondo(estado.getColorFondo())
                .colorTexto(estado.getColorTexto())
                .nombre(estado.getNombre())
                .tipo(estado.getTipo().name())
                .valor(estado.getValor())
                .build();
    }


}
