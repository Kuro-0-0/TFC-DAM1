package com.salesianostriana.dam.GarciaMariaPablo.admin.daos.estado;


import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EstadoDao_Modificar {

    private long id;
    private String nombre;
    private boolean activo;
    private String colorTexto;
    private String tipo;
    private String colorFondo;

    public static EstadoDao_Modificar crearDao(Estado estado) {
        return EstadoDao_Modificar.builder()
                .id(estado.getId())
                .nombre(estado.getNombre())
                .activo(estado.isActivo())
                .colorFondo(estado.getColorFondo())
                .colorTexto(estado.getColorTexto())
                .tipo(estado.getTipo().name())
                .build();
    }



}
