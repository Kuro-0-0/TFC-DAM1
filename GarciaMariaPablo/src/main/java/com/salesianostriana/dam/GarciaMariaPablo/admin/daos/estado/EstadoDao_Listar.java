package com.salesianostriana.dam.GarciaMariaPablo.admin.daos.estado;


import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstadoDao_Listar {

    private long id;
    private String nombre;
    private String colorFondo;
    private String colorTexto;
    private String valor;
    private boolean activo;

    public static EstadoDao_Listar crearDao(Estado estado) {
        return EstadoDao_Listar.builder()
                .id(estado.getId())
                .nombre(estado.getNombre())
                .colorFondo(estado.getColorFondo())
                .colorTexto(estado.getColorTexto())
                .valor(estado.getValor())
                .activo(estado.isActivo())
                .build();

    }
}
