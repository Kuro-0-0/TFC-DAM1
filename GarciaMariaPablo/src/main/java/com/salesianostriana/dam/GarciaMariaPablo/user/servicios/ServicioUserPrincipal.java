package com.salesianostriana.dam.GarciaMariaPablo.user.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Estado;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Incidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.TipoEstados;
import com.salesianostriana.dam.GarciaMariaPablo.global.seguridad.ServicioSeguridad;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.ServicioIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.ServicioUsuario;
import com.salesianostriana.dam.GarciaMariaPablo.user.daos.incidencia.external.IncidenciaDao_RecientesDashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
public class ServicioUserPrincipal {

    @Autowired
    private ServicioSeguridad seguridad;
    @Autowired
    private ServicioUserIncidencias servicioUserIncidencias;
    @Autowired
    private ServicioUsuario servicioUsuario;
    @Autowired
    private ServicioIncidencia servicioIncidencia;

    public String cargarDashboard(Model model, RedirectAttributes redirectAttributes, int  limiteRecientes ) {
        Usuario usuario  = seguridad.obtenerUsuarioLogado();
        List<Incidencia> incidencias = servicioIncidencia.getIncidenciasPorIdReportante(usuario.getId());
        List<IncidenciaDao_RecientesDashboard> incidenciasRecientes = incidencias
                .stream()
                .sorted(Comparator.comparing(Incidencia::getFechaIEA).reversed())
                .limit(limiteRecientes)
                .map(IncidenciaDao_RecientesDashboard::crearDao)
                .toList();
        int[] estadisticasRapidas = {0,0,0};


        incidencias
                .stream()
                .map(Incidencia::getEstado)
                .map(Estado::getTipo)
                .forEach(t -> {
                    switch (t) {
                        case TipoEstados.Comienzo -> estadisticasRapidas[0]++;
                        case TipoEstados.Proceso -> estadisticasRapidas[1]++;
                        case TipoEstados.Final -> estadisticasRapidas[2]++;
                        default -> {
                            System.out.println("Tipo no valido" + t);
                        }
                    }
                });

        model.addAttribute("incidenciasRecientes",incidenciasRecientes);

        model.addAttribute("ultimaActualizacion", incidenciasRecientes.isEmpty() ? "NUNCA" :
                DAYS.between(incidenciasRecientes.getFirst().getFecha(),LocalDateTime.now()));

        model.addAttribute("estadisticasRapidas", estadisticasRapidas);
        model.addAttribute("nombre", usuario.getNombre() + ' ' + usuario.getApellidos());

        return "user/otros/dashboard";
    }
}
