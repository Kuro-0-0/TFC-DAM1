package com.salesianostriana.dam.GarciaMariaPablo.admin.servicios;


import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.estado.external.EstadoDao_FiltrarIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.incidencia.IncidenciaAdminDao_Crear;
import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.incidencia.IncidenciaAdminDao_Modificar;
import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.incidencia.IncidenciaDao_Estadisticas;
import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.otros.EstadisticasDao;
import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.usuario.external.UsuarioDao_Estadisticas;
import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.usuario.external.UsuarioDao_FiltrarIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.usuario.external.UsuarioDao_FormularioIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.estado.external.EstadoDao_Seleccionar;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.incidencia.IncidenciaDao_Inspeccionar;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.incidencia.IncidenciaDao_Listar;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.HistorialEstados;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Incidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.RolesUsuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.repositorios.RepositorioEstado;
import com.salesianostriana.dam.GarciaMariaPablo.global.repositorios.RepositorioHistorialEstados;
import com.salesianostriana.dam.GarciaMariaPablo.global.repositorios.RepositorioIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.repositorios.RepositorioUsuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.ServicioIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.ServicioUsuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.otros.base.ServicioBaseImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class ServicioAdminIncidencia extends ServicioBaseImpl<Incidencia, Long, RepositorioIncidencia> {


    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioEstado repositorioEstado;

    @Autowired
    private RepositorioHistorialEstados repositorioHistorialEstados;

    @Autowired
    private ServicioIncidencia servicioIncidencia;
    @Autowired
    private ServicioUsuario servicioUsuario;


    public Incidencia revertirDao(IncidenciaAdminDao_Crear incidenciaDao) {
        Usuario reportante = incidenciaDao.getReportante() == null ? repositorioUsuario.findByUsername("sin-reportante").orElseThrow() : repositorioUsuario.findById(incidenciaDao.getReportante()).orElse(repositorioUsuario.findByUsername("sin-reportante").orElseThrow());
        Usuario tecnico = incidenciaDao.getTecnico() == null ? repositorioUsuario.findByUsername("sin-tecnico").orElseThrow() : repositorioUsuario.findById(incidenciaDao.getTecnico()).orElse(repositorioUsuario.findByUsername("sin-tecnico").orElseThrow());
        return Incidencia.builder()
                .titulo(incidenciaDao.getTitulo())
                .ubicacion(incidenciaDao.getUbicacion())
                .descripcion(incidenciaDao.getDescripcion())
                .reportante(reportante)
                .tecnico(tecnico)
                .estado(repositorioEstado.findByValor(incidenciaDao.getEstado()).orElse(repositorioEstado.findByValor("sin-estado").orElseThrow()))
                .fechaIEA(LocalDateTime.now())
                .fechaCreacion(LocalDateTime.now())
                .fechaModificacion(LocalDateTime.now())
                .build();
    }


    public Incidencia revertirDao(IncidenciaAdminDao_Modificar incidenciaDao) {
        return Incidencia.builder()
                .id(incidenciaDao.getId())
                .titulo(incidenciaDao.getTitulo())
                .ubicacion(incidenciaDao.getUbicacion())
                .descripcion(incidenciaDao.getDescripcion())
                .reportante(repositorioUsuario.findById(incidenciaDao.getReportante().getId()).orElse(repositorioUsuario.findByUsername("sin-reportante").orElseThrow()))
                .tecnico(repositorioUsuario.findById(incidenciaDao.getTecnico().getId()).orElse(repositorioUsuario.findByUsername("sin-tecnico").orElseThrow()))
                .estado(repositorioEstado.findByValor(incidenciaDao.getEstado().getValor()).orElse(repositorioEstado.findByValor("sin-estado").orElseThrow()))
                .fechaIEA(incidenciaDao.getFechaCreacion())
                .fechaCreacion(incidenciaDao.getFechaCreacion())
                .fechaModificacion(LocalDateTime.now())
                .build();
    }


    public String listar(Model model, String paginaStr, String perPageStr, String ordenPor, Boolean ordenAsc,
                         List<String> reportantesSTR, List<String> estados, String filtroTitulo, String filtroUbicacion,
                         String mostrarDesactivadosStr) {
        List<Long> reportantes = null;
        List<Incidencia> incidencias;
        boolean mostrarDesactivados = mostrarDesactivadosStr.equalsIgnoreCase("on");
        int incidenciasTotal;
        List<EstadoDao_FiltrarIncidencia> listarEstados = repositorioEstado.findByActivo(true).stream()
                .map(EstadoDao_FiltrarIncidencia::crearDao)
                .toList();

        List<UsuarioDao_FiltrarIncidencia> listarReportantes = repositorioUsuario.findByRol(RolesUsuario.USER).stream()
                .map(UsuarioDao_FiltrarIncidencia::crearDao)
                .toList();

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

        incidencias = repositorio.listFilters(reportantes, estados, filtroTitulo, filtroUbicacion, mostrarDesactivados);
        incidencias = procesarOrden(incidencias, ordenAsc, ordenPor);
        incidenciasTotal = incidencias.size();
        incidencias = procesarPaginacion(incidencias, model, paginaStr, perPageStr);

        model.addAttribute("incidencias", incidencias.stream().map(IncidenciaDao_Listar::crearDao).toList());

        model.addAttribute("incidenciasTotal",incidenciasTotal);
        model.addAttribute("reportantes", listarReportantes);
        model.addAttribute("estados", listarEstados);
        model.addAttribute("filtroTitulo", filtroTitulo);
        model.addAttribute("filtroUbicacion", filtroUbicacion);
        model.addAttribute("mostrarDesactivados", mostrarDesactivados);

        return "admin/incidencia/listar";
    }


    private List<Incidencia> procesarOrden(List<Incidencia> incidencias, Boolean ordenAsc, String ordenPor) {
    	if (ordenPor != null) {
    		Comparator<Incidencia> comparator = switch (ordenPor) {
                case "titulo" -> Comparator.comparing(Incidencia::getTitulo);
                case "ubicacion" -> Comparator.comparing(Incidencia::getUbicacion);
                case "reportante" -> Comparator.comparing(Incidencia::getReportante);
                case "estado" -> Comparator.comparing(Incidencia::getNombreEstado);
                case "fechaCreacion" -> Comparator.comparing(Incidencia::getFechaIEA);
                default -> Comparator.comparing(Incidencia::getFechaIEA).reversed();
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
        model.addAttribute("incidenciaDAO", new IncidenciaAdminDao_Crear());
        model.addAttribute("estados", repositorioEstado.	findByActivo(true).stream()
                .map(EstadoDao_Seleccionar::crearDao)
                .toList());
        model.addAttribute("modificar",false);

        return "admin/incidencia/formulario";
    }


    public String cargarModificar(Model model, long id) {
        cargarListas(model);
        model.addAttribute("incidenciaDAO", IncidenciaAdminDao_Modificar.crearDao(findById(id).orElseThrow()));
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
                        case RolesUsuario.TECH -> tecnicos.add(u);
                        case RolesUsuario.USER -> reportantes.add(u);
                    }
                });

        model.addAttribute("reportantes", reportantes);
        model.addAttribute("tecnicos", tecnicos);
    }


    public String crear(IncidenciaAdminDao_Crear incidenciaDao) {
        save(revertirDao(incidenciaDao));
        return "redirect:/admin/incidencias";
    }


    public String modificar(IncidenciaAdminDao_Modificar incidenciaDao) {

        Incidencia antiguaIncidencia = findById(incidenciaDao.getId()).orElseThrow();
        Incidencia nuevaIncidencia = revertirDao(incidenciaDao);

        if (antiguaIncidencia.getEstado() != nuevaIncidencia.getEstado()) {

            if  (antiguaIncidencia.getFechaIEA().equals(nuevaIncidencia.getFechaIEA())) {
                nuevaIncidencia.setFechaIEA(LocalDateTime.now());
            }

            HistorialEstados nuevaEntrada = HistorialEstados.builder()
                    .incidencia(nuevaIncidencia)
                    .fechaComienzo(antiguaIncidencia.getFechaIEA())
                    .fechaFinal(nuevaIncidencia.getFechaIEA())
                    .estadoActual(nuevaIncidencia.getEstado())
                    .estadoInicial(antiguaIncidencia.getEstado())
                    .build();

            nuevaIncidencia.anadirRegistroHistorialEstados(nuevaEntrada);

            repositorioHistorialEstados.save(nuevaEntrada);
        }

        edit(nuevaIncidencia);
        return "redirect:/admin/incidencias";
    }

    public String eliminar(long id, RedirectAttributes redirectAttributes) {
        if (!servicioIncidencia.eliminar(id)) {
            redirectAttributes.addFlashAttribute("error","Algo ha salido mal durante la eliminacion de la incidencia.");
        }
        return "redirect:/admin/incidencias";
    }

    public String cargarEstadisticas(Model model) {
        List<Usuario> usuarios = servicioUsuario.findAll();
        List<UsuarioDao_Estadisticas> usuariosDao = usuarios.stream().map(UsuarioDao_Estadisticas::crearDao).toList();

        EstadisticasDao estadisticasDao = new EstadisticasDao(
                usuariosDao.stream().filter(u -> u.getNumeroResueltas() > 0).sorted(Comparator.comparing(UsuarioDao_Estadisticas::getNumeroResueltas).reversed()).limit(3).toList(),
                usuariosDao.stream().filter(u -> u.getHorasMedias() > 0).sorted(Comparator.comparing(UsuarioDao_Estadisticas::getHorasMedias)).limit(3).toList(),
                usuariosDao.stream().filter(u -> u.getNumeroReportes() > 0).sorted(Comparator.comparing(UsuarioDao_Estadisticas::getNumeroReportes).reversed()).limit(3).toList()
        );

        model.addAttribute("estadisticasJAVA",estadisticasDao);


        model.addAttribute("incidencias",findAll().stream()
        		.filter(i -> i.getEstado().isActivo())
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
