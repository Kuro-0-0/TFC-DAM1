package com.salesianostriana.dam.GarciaMariaPablo.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.daos.estado.external.EstadoDao_FiltrarIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.daos.estado.external.EstadoDao_Seleccionar;
import com.salesianostriana.dam.GarciaMariaPablo.daos.incidencia.*;
import com.salesianostriana.dam.GarciaMariaPablo.daos.usuario.external.UsuarioDao_FiltrarIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.daos.usuario.external.UsuarioDao_FormularioIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.modelos.HistorialEstados;
import com.salesianostriana.dam.GarciaMariaPablo.modelos.Incidencia;
import com.salesianostriana.dam.GarciaMariaPablo.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.modelos.utilidades.Rol;
import com.salesianostriana.dam.GarciaMariaPablo.repositorios.RepositorioEstado;
import com.salesianostriana.dam.GarciaMariaPablo.repositorios.RepositorioHistorialEstados;
import com.salesianostriana.dam.GarciaMariaPablo.repositorios.RepositorioIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.repositorios.RepositorioUsuario;
import com.salesianostriana.dam.GarciaMariaPablo.servicios.base.ServicioBaseImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class ServicioIncidencia extends ServicioBaseImpl<Incidencia, Long, RepositorioIncidencia> {


    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioEstado repositorioEstado;

    @Autowired
    private RepositorioHistorialEstados repositorioHistorialEstados;



    public Incidencia revertirDao(IncidenciaDao_Crear incidenciaDao) {
        Usuario reportante = incidenciaDao.getReportante() == null ? repositorioUsuario.findByUsername("sin-reportante") : repositorioUsuario.findById(incidenciaDao.getReportante()).orElse(repositorioUsuario.findByUsername("sin-reportante"));
        Usuario tecnico = incidenciaDao.getTecnico() == null ? repositorioUsuario.findByUsername("sin-tecnico") : repositorioUsuario.findById(incidenciaDao.getTecnico()).orElse(repositorioUsuario.findByUsername("sin-tecnico"));
        return Incidencia.builder()
                .titulo(incidenciaDao.getTitulo())
                .ubicacion(incidenciaDao.getUbicacion())
                .descripcion(incidenciaDao.getDescripcion())
                .reportante(reportante)
                .tecnico(tecnico)
                .estado(repositorioEstado.findByValor(incidenciaDao.getEstado()).orElse(repositorioEstado.findByValor("sin-estado").orElseThrow()))
                .fechaCreacion(incidenciaDao.getFechaCreacion())
                .build();
    }


    public Incidencia revertirDao(IncidenciaDao_Modificar incidenciaDao) {
        return Incidencia.builder()
                .id(incidenciaDao.getId())
                .titulo(incidenciaDao.getTitulo())
                .ubicacion(incidenciaDao.getUbicacion())
                .descripcion(incidenciaDao.getDescripcion())
                .reportante(repositorioUsuario.findById(incidenciaDao.getReportante().getId()).orElse(repositorioUsuario.findByUsername("sin-reportante")))
                .tecnico(repositorioUsuario.findById(incidenciaDao.getTecnico().getId()).orElse(repositorioUsuario.findByUsername("sin-tecnico")))
                .estado(repositorioEstado.findByValor(incidenciaDao.getEstado().getValor()).orElse(repositorioEstado.findByValor("sin-estado").orElseThrow()))
                .fechaCreacion(incidenciaDao.getFechaCreacion())
                .build();
    }


    public String listar(Model model, String paginaStr, String perPageStr, String ordenPor, Boolean ordenAsc,
                         List<String> reportantesSTR, List<String> estados, String filtroTitulo, String filtroUbicacion,
                         String mostrarDesactivadosStr) {
        List<Long> reportantes = null;
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
            if (reportantesSTR != null && !reportantesSTR.isEmpty()) {
                reportantes = reportantesSTR.stream().map(Long::parseLong).toList();
            }
        } catch (Exception e) {
            log.error("e:", e);
        }

        reportantes = reportantes == null || reportantes.isEmpty() ? null : reportantes;
        estados = estados == null || estados.isEmpty() ? null : estados;

        if (reportantes != null) {
            List<Long> reportantesLambda = reportantes;
            listarReportantes.forEach(
                    reportante -> {
                        if (reportantesLambda.contains(reportante.getId())) {
                            reportante.setSelected(true);
                        }
                    }
            );
        }

        if (estados != null) {
            List<String> estadosLambda = estados.stream().map(String::toLowerCase).toList();
            listarEstados.forEach(
                    estado -> {
                        if (estadosLambda.contains(estado.getValor().toLowerCase())) {
                            estado.setSelected(true);
                        }
                    }
            );
        }

        /* Procesar incidencias*/
        incidencias = repositorio.listFilters(reportantes, estados, filtroTitulo, filtroUbicacion, mostrarDesactivados);
        incidencias = procesarOrden(incidencias, ordenAsc, ordenPor);
        incidencias = procesarPaginacion(incidencias, model, paginaStr, perPageStr);

        model.addAttribute("incidencias", incidencias.stream().map(IncidenciaDao_Listar::crearDao).toList());

        /* Atributos extra de la pagina listar */
        model.addAttribute("reportantes", listarReportantes);
        model.addAttribute("estados", listarEstados);
        model.addAttribute("filtroTitulo", filtroTitulo);
        model.addAttribute("filtroUbicacion", filtroUbicacion);
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


    public String inspeccionar(Model model, long id) {
        model.addAttribute("incidencia", IncidenciaDao_Inspeccionar.crearDao(findById(id).orElseThrow()));
        return "admin/incidencia/inspeccionar";
    }


    public String cargarCrear(Model model) {
        cargarListas(model);
        model.addAttribute("incidenciaDAO", new IncidenciaDao_Crear());
        model.addAttribute("estados", repositorioEstado.findByActivo(true).stream()
                .map(EstadoDao_Seleccionar::crearDao)
                .toList());
        model.addAttribute("modificar",false);

        return "admin/incidencia/formulario";
    }


    public String cargarModificar(Model model, long id) {
        cargarListas(model);
        model.addAttribute("incidenciaDAO",IncidenciaDao_Modificar.crearDao(findById(id).orElseThrow()));
        model.addAttribute("estados", repositorioEstado.findByActivo(true).stream()
                .map(EstadoDao_Seleccionar::crearDao)
                .toList());
        model.addAttribute("modificar",true);
        return "admin/incidencia/formulario";
    }


    private void cargarListas(Model model) {
        List<UsuarioDao_FormularioIncidencia> reportantes = new ArrayList<>();
        List<UsuarioDao_FormularioIncidencia> tecnicos = new ArrayList<>();

        repositorioUsuario.findAll().stream()
                .map(UsuarioDao_FormularioIncidencia::crearDao)
                .forEach(u -> {
                    switch (u.getRol()) {
                        case Rol.tecnico -> tecnicos.add(u);
                        case Rol.reportante -> reportantes.add(u);
                    }
                });

        model.addAttribute("reportantes", reportantes);
        model.addAttribute("tecnicos", tecnicos);
    }


    public String crear(IncidenciaDao_Crear incidenciaDao) {
        save(revertirDao(incidenciaDao));
        return "redirect:/incidencias";
    }


    public String modificar(IncidenciaDao_Modificar incidenciaDao) {

        Incidencia antiguaIncidencia = findById(incidenciaDao.getId()).orElseThrow();
        Incidencia nuevaIncidencia = revertirDao(incidenciaDao);

        if (antiguaIncidencia.getEstado() != nuevaIncidencia.getEstado()) {

            if  (antiguaIncidencia.getFechaCreacion().equals(nuevaIncidencia.getFechaCreacion())) {
                nuevaIncidencia.setFechaCreacion(LocalDate.now());
            }

            HistorialEstados nuevaEntrada = HistorialEstados.builder()
                    .incidencia(nuevaIncidencia)
                    .fechaComienzo(antiguaIncidencia.getFechaCreacion())
                    .fechaFinal(nuevaIncidencia.getFechaCreacion())
                    .estadoActual(nuevaIncidencia.getEstado())
                    .estadoInicial(antiguaIncidencia.getEstado())
                    .build();

            nuevaIncidencia.anadirRegistroHistorialEstados(nuevaEntrada);

            repositorioHistorialEstados.save(nuevaEntrada);
        }

        edit(nuevaIncidencia);
        return "redirect:/incidencias";
    }

    public String eliminar(long id) {
        Incidencia i = findById(id).orElseThrow();
        i.eliminarEstado();
        i.eliminarUsuarios();
        delete(i);
        return "redirect:/incidencias";
    }

    public String cargarEstadisticas(Model model) {
        model.addAttribute("incidencias",findAll().stream()
                .map(IncidenciaDao_Estadisticas::crearDao)
                .toList());

        /*
        Ahora que tenemos historial de estados se pueden hacer estadísticas como por ejemplo:
            - Tiempo medio de cambio de estado.
            - Tiempo medio de cambio de estado por usuario
            - Incidencias por tipos
            - Incidencia con el tiempo más largo
            - Incidencia con el tiempo más corto.
         */

        return "admin/incidencia/estadisticas";
    }
}
