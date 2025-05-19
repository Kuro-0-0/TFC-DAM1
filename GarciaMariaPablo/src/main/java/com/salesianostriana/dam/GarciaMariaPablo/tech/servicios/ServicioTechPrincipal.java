package com.salesianostriana.dam.GarciaMariaPablo.tech.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Incidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.TipoEstados;
import com.salesianostriana.dam.GarciaMariaPablo.global.seguridad.ServicioSeguridad;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.ServicioIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.tech.daos.incidencia.IncidenciaDao_Dashboard;
import com.salesianostriana.dam.GarciaMariaPablo.tech.daos.otros.EstadisticasDao_Dashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class ServicioTechPrincipal {

    @Autowired
    private ServicioSeguridad seguridad;
    @Autowired
    private ServicioIncidencia servicioIncidencia;

    public String cargarDashboard(Model model, RedirectAttributes redirectAttributes, int incidenciasRecientes) {
        Usuario usuario = seguridad.obtenerUsuarioLogado();
        int[] incidenciasEstadisiticas = {0,0,0};
        List<Incidencia> incidencias = servicioIncidencia.getIncidenciasPorIdTecnico(usuario.getId());
        List<Incidencia> incidenciasSinAsignar = servicioIncidencia.getIncidenciasSinAsignar();

        incidenciasEstadisiticas[2] = incidenciasSinAsignar.size();
        incidenciasEstadisiticas[1] = incidencias.stream()
                .filter(i -> i.getEstado().getTipo() != TipoEstados.Final)
                .toList()
                .size();
        incidenciasEstadisiticas[0] = incidencias.stream()
                .filter(i -> i.getEstado().getTipo() == TipoEstados.Final)
                .filter(i -> i.getFechaIEA()
                        .isAfter(LocalDateTime.now().minusDays(30))
                    && i.getFechaIEA()
                        .isBefore(LocalDateTime.now()))
                .toList()
                .size();

        model.addAttribute("estadisticas", EstadisticasDao_Dashboard.crearDao(incidenciasEstadisiticas));
        model.addAttribute("nombreTecnico", usuario.getNombre() + " " + usuario.getApellidos());

        model.addAttribute("incidenciasAsignadas", incidencias.stream()
//                .filter(i -> i.getEstado().getTipo() != TipoEstados.Final)
                .sorted(Comparator.comparing(Incidencia::getFechaModificacion).reversed())
                .limit(incidenciasRecientes)
                .map(IncidenciaDao_Dashboard::crearDao)
                .toList());
        model.addAttribute("incidenciasSinAsignar", incidenciasSinAsignar.stream()
                .sorted(Comparator.comparing(Incidencia::getFechaModificacion).reversed())
                .limit(incidenciasRecientes)
                .map(IncidenciaDao_Dashboard::crearDao)
                .toList());


        return "tech/otros/dashboard";
    }

}

