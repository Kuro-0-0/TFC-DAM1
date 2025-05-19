package com.salesianostriana.dam.GarciaMariaPablo.tech.daos.incidencia;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Incidencia;
import com.salesianostriana.dam.GarciaMariaPablo.tech.daos.estado.external.EstadoTechDao_ListarIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.tech.daos.usuario.external.UsuarioTechDao_ListarIncidencia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
@NoArgsConstructor @Builder
public class IncidenciaTechDao_Listar {

    private long id;
    private String titulo;
    private EstadoTechDao_ListarIncidencia estado;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private UsuarioTechDao_ListarIncidencia reportante;
    private UsuarioTechDao_ListarIncidencia tecnico;

    public static IncidenciaTechDao_Listar crearDao(Incidencia incidencia) {
        return IncidenciaTechDao_Listar.builder()
                .id(incidencia.getId())
                .titulo(incidencia.getTitulo())
                .descripcion(incidencia.getDescripcion())
                .estado(EstadoTechDao_ListarIncidencia.crearDao(incidencia.getEstado()))
                .fechaCreacion(incidencia.getFechaCreacion())
                .fechaModificacion(incidencia.getFechaModificacion())
                .reportante(UsuarioTechDao_ListarIncidencia.crearDao(incidencia.getReportante()))
                .tecnico(UsuarioTechDao_ListarIncidencia.crearDao(incidencia.getTecnico()))
                .build();
    }

}
