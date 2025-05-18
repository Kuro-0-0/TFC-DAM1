package com.salesianostriana.dam.GarciaMariaPablo.user.daos.incidencia;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Incidencia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @Data @AllArgsConstructor @NoArgsConstructor
public class IncidenciaUserDao_Modificar {

    private long id;
    private String titulo, descripcion,ubicacion;

    public static IncidenciaUserDao_Modificar crearDao(Incidencia incidencia) {
        System.out.println(incidencia);
        return IncidenciaUserDao_Modificar.builder()
                .id(incidencia.getId())
                .titulo(incidencia.getTitulo())
                .descripcion(incidencia.getDescripcion())
                .ubicacion(incidencia.getUbicacion())
                .build();
    }

}
