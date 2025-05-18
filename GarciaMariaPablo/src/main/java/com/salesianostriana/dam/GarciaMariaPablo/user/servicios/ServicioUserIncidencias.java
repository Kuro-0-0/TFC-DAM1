package com.salesianostriana.dam.GarciaMariaPablo.user.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.global.daos.estado.external.EstadoDao_Seleccionar;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.incidencia.IncidenciaDao_Inspeccionar;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.incidencia.IncidenciaDao_Listar;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Estado;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Incidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.TipoEstados;
import com.salesianostriana.dam.GarciaMariaPablo.global.repositorios.RepositorioIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.seguridad.ServicioSeguridad;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.otros.base.ServicioBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Service
public class ServicioUserIncidencias extends ServicioBaseImpl<Incidencia,Long, RepositorioIncidencia> {

    @Autowired
    private ServicioSeguridad seguridad;

    @Autowired
    private ServicioUserEstado servicioUserEstado;

    public String inspeccionar(long id, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = seguridad.obtenerUsuarioLogado();
        Incidencia incidencia = findById(id).orElseThrow();
        if (incidencia.getReportante().getId() != usuario.getId()) {
            redirectAttributes.addFlashAttribute("error","No puedes inspeccionar incidencias que no son de tu propiedad.");
            return "redirect:/user/dashboard";
        }

        model.addAttribute("usuarioRol", "USER");
        model.addAttribute("incidencia", IncidenciaDao_Inspeccionar.crearDao(incidencia));

        return "user/incidencia/inspeccionar";
    }

    public String listar(Model model, RedirectAttributes redirectAttributes,String buscar, String perPage,String paginaNum,String estadoValue) {
        Usuario usuario = seguridad.obtenerUsuarioLogado();
        int estadisticasRapidas[] = {0,0,0};
        List<Incidencia> incidencias = usuario.getIncidenciasReportadas();
        List <IncidenciaDao_Listar> listaFinal;


        model.addAttribute("totalIncidencias", incidencias.size());
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

        listaFinal = incidencias.stream()
                .filter(i -> buscar != null ? i.getTitulo().toLowerCase().contains(buscar.toLowerCase()) : true)
                .filter(i -> estadoValue != null && !estadoValue.isBlank() ? i.getEstado().getValor().equals(estadoValue) : true)
                .map(IncidenciaDao_Listar::crearDao)
                .toList();

        model.addAttribute("estados", servicioUserEstado
                .findAll()
                .stream()
                .map(EstadoDao_Seleccionar::crearDao)
                .toList());

        model.addAttribute("datosIncidencias", estadisticasRapidas);

        incidencias = procesarPaginacion(incidencias,model,paginaNum,perPage);
        model.addAttribute("mostrandoAhora",incidencias.size());
        model.addAttribute("incidencias",listaFinal);

        return "user/incidencia/listar";
    }
}
