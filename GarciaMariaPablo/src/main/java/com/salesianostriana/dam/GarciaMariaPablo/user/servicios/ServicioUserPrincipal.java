package com.salesianostriana.dam.GarciaMariaPablo.user.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Estado;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Incidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.TipoEstados;
import com.salesianostriana.dam.GarciaMariaPablo.global.seguridad.ServicioSeguridad;
import com.salesianostriana.dam.GarciaMariaPablo.user.daos.incidencia.external.IncidenciaDao_RecientesDashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;

@Controller
public class ServicioUserPrincipal {

    @Autowired
    private ServicioSeguridad seguridad;
    @Autowired
    private ServicioUserIncidencias servicioUserIncidencias;

    //private Usuario usuarioLogueado;

//    @PostConstruct
//    public void init() {
//        usuarioLogueado = seguridad.obtenerUsuarioLogado();
//    }



    public String cargarDashboard(Model model, RedirectAttributes redirectAttributes, int  limiteRecientes ) {
        List<Incidencia> incidencias = seguridad.obtenerUsuarioLogado().getIncidenciasReportadas();
        int estadisticasRapidas[] = {0,0,0};


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
                            System.out.println("Tipo no valido");
                        }
                    }
                });

        model.addAttribute("incidenciasRecientes",incidencias
                .stream()
                .sorted(Comparator.comparing(Incidencia::getFechaCreacion))
                .limit(limiteRecientes)
                .map(IncidenciaDao_RecientesDashboard::crearDao)
                .toList());


        model.addAttribute("estadisticasRapidas", estadisticasRapidas);

        return "user/otros/dashboard";
    }
}
