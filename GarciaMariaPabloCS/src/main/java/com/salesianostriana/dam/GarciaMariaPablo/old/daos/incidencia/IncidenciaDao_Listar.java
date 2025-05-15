package com.salesianostriana.dam.GarciaMariaPablo.old.daos.incidencia;

import com.salesianostriana.dam.GarciaMariaPablo.old.daos.estado.external.EstadoDao_ListarIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.old.daos.usuario.external.UsuarioDao_ListarIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.old.modelos.Incidencia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Builder @Data @AllArgsConstructor @NoArgsConstructor
public class IncidenciaDao_Listar {

    private long id;
    private String titulo;
    private String ubicacion;
    private UsuarioDao_ListarIncidencia reportante;
    private EstadoDao_ListarIncidencia estado;
    private String fechaCreacion;

    public static IncidenciaDao_Listar crearDao(Incidencia incidencia) {
        return IncidenciaDao_Listar.builder()
                .id(incidencia.getId())
                .titulo(incidencia.getTitulo())
                .ubicacion(incidencia.getUbicacion())
                .reportante(UsuarioDao_ListarIncidencia.crearDao(incidencia.getReportante()))
                .estado(EstadoDao_ListarIncidencia.crearDao(incidencia.getEstado()))
                .fechaCreacion(incidencia.getFechaCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();
    }

}
