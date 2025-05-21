package com.salesianostriana.dam.GarciaMariaPablo.admin.daos.usuario.external;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Incidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.RolesUsuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.TipoEstados;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class UsuarioDao_Estadisticas {

    private String nombreCompleto;
    private String nombre;
    private String apellido;
    private long numeroResueltas;
    private double horasMedias;
    private long numeroReportes;

    public static double calcularHorasMedias(List<Incidencia> lista) {
         List<Incidencia> listaIntermedia = lista
                 .stream()
                 .filter(i -> i != null)
                 .filter(i -> i.getEstado().getTipo().equals(TipoEstados.Final))
                 .toList();
         return !listaIntermedia.isEmpty() ? listaIntermedia.stream()
                 .mapToDouble(i -> ChronoUnit.HOURS.between(i.getFechaCreacion(),i.getFechaModificacion()))
                 .average()
                 .orElse(0L)
                 : -1;

    }

    public static UsuarioDao_Estadisticas crearDao(Usuario usuario) {
        return UsuarioDao_Estadisticas.builder()
                .nombreCompleto(usuario.getNombre() + ' '  + usuario.getApellidos())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellidos())
                .numeroResueltas(usuario.getRol() == RolesUsuario.TECH ?
                        usuario.getIncidenciasGestionadas()
                        .stream()
                        .filter(i  -> i.getEstado().getTipo().equals(TipoEstados.Final))
                        .count() : -1)
                .numeroReportes(usuario.getRol() == RolesUsuario.USER ? usuario.getIncidenciasReportadas()
                        .stream()
                        .filter(i  -> i != null ? i.getEstado().getTipo().equals(TipoEstados.Final) : true)
                        .count() : -1)
                .horasMedias(usuario.getRol()  == RolesUsuario.TECH ? calcularHorasMedias(usuario.getIncidenciasGestionadas()) : -1)
                .build();
    }

}
