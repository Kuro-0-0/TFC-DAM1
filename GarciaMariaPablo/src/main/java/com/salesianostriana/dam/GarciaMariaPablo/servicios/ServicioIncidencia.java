package com.salesianostriana.dam.GarciaMariaPablo.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.daos.estado.external.EstadoDao_FiltrarIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.daos.incidencia.IncidenciaDao_Listar;
import com.salesianostriana.dam.GarciaMariaPablo.daos.usuario.external.UsuarioDao_FiltrarIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.modelos.Incidencia;
import com.salesianostriana.dam.GarciaMariaPablo.modelos.utilidades.Rol;
import com.salesianostriana.dam.GarciaMariaPablo.repositorios.RepositorioEstado;
import com.salesianostriana.dam.GarciaMariaPablo.repositorios.RepositorioIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.repositorios.RepositorioUsuario;
import com.salesianostriana.dam.GarciaMariaPablo.servicios.base.ServicioBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class ServicioIncidencia extends ServicioBase<Incidencia, Long, RepositorioIncidencia> {

    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private RepositorioEstado repositorioEstado;

    public String listar(Model model, String paginaStr, String perPageStr, String ordenPor, Boolean ordenAsc,
                         List<String> reportantesSTR, List<String> estados, String filtroTitulo, String filtroUbicacion,
                         String mostrarDesactivadosStr) {

        List<Long> reportantes, finalReportantes = new ArrayList<>();
        List<Incidencia> incidencias;
        boolean mostrarDesactivados = mostrarDesactivadosStr.equalsIgnoreCase("on");

        List<EstadoDao_FiltrarIncidencia> listarEstados = repositorioEstado.findByActivo(true).stream()
                .map(EstadoDao_FiltrarIncidencia::crearDao)
                .toList();

        List<UsuarioDao_FiltrarIncidencia> listarReportantes = repositorioUsuario.findByRol(Rol.reportante).stream()
                .map(UsuarioDao_FiltrarIncidencia::crearDao)
                .toList();

        /*Procesar filtros antes de enviar al repositorio.*/
        try {
            reportantesSTR.forEach(s -> finalReportantes.add(Long.parseLong(s)));
        } catch (Exception e) {
            log.error("e: ", e);
        }

        reportantes = finalReportantes; // Evitar que la lambda pete.
        reportantes = reportantes.isEmpty() ? null : reportantes;
        estados = estados == null || estados.isEmpty() ? null : estados;

        /* Procesar incidencias*/
        incidencias = repositorio.listFilters(reportantes,estados,filtroTitulo,filtroUbicacion, mostrarDesactivados);
        incidencias = procesarOrden(incidencias,ordenAsc,ordenPor);
        incidencias = procesarPaginacion(incidencias,model, paginaStr, perPageStr);

        model.addAttribute("incidencias",incidencias.stream().map(IncidenciaDao_Listar::crearDao).toList());

        /* Atributos extra de la pagina listar */
        model.addAttribute("reportantes", listarReportantes);
        model.addAttribute("estados", listarEstados);
        model.addAttribute("filtroTitulo",filtroTitulo);
        model.addAttribute("filtroUbicacion",filtroUbicacion);
        model.addAttribute("mostrarDesactivados", mostrarDesactivados);
        model.addAttribute("mostrandoAhora", incidencias.size());

        return "admin/incidencia/listar";
    }

    private List<Incidencia> procesarOrden(List<Incidencia> incidencias, Boolean ordenAsc, String ordenPor) {
        if (ordenPor != null) {
            Comparator<Incidencia> comparator = switch (ordenPor) {
                case "titulo" -> Comparator.comparing(Incidencia::getTitulo);
                case "ubicacion" -> Comparator.comparing(Incidencia::getUbicacion);
                case "reportante" -> Comparator.comparing(Incidencia::getReportante);
                case "estado" -> Comparator.comparing(Incidencia::getNombreEstado);
                case "fechaCreacion" -> Comparator.comparing(Incidencia::getFechaCreacion);
                default -> Comparator.comparing(Incidencia::getId);
            };

            if (!ordenAsc) {
                comparator = comparator.reversed();
            }
            incidencias.sort(comparator);
        }
        return incidencias;
    }

}
