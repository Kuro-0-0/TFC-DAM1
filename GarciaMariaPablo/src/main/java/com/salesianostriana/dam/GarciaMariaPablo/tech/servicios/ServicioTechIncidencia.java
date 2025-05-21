package com.salesianostriana.dam.GarciaMariaPablo.tech.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.global.daos.incidencia.IncidenciaDao_Inspeccionar;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Incidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.TipoEstados;
import com.salesianostriana.dam.GarciaMariaPablo.global.seguridad.ServicioSeguridad;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.ServicioEstado;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.ServicioIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.ServicioUsuario;
import com.salesianostriana.dam.GarciaMariaPablo.tech.daos.estado.EstadoTechDao_Seleccionar;
import com.salesianostriana.dam.GarciaMariaPablo.tech.daos.estado.external.EstadoTechDao_ModificarIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.tech.daos.incidencia.IncidenciaTechDao_Listar;
import com.salesianostriana.dam.GarciaMariaPablo.tech.daos.otros.EstadisticasDao_Listar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioTechIncidencia {

    @Autowired
    private ServicioIncidencia servicioIncidencia;

    @Autowired
    private ServicioSeguridad seguridad;
    @Autowired
    private ServicioEstado servicioEstado;
    @Autowired
    private ServicioUsuario servicioUsuario;

    public String listarAsignadas(Model model, String q, String perPage, String pageNum, String estado) {
        Usuario usuario = seguridad.obtenerUsuarioLogado();
        List<Incidencia> incidencias = servicioIncidencia.getIncidenciasPorIdTecnico(usuario.getId());
        incidencias.sort(Comparator.comparing(Incidencia::getFechaModificacion).reversed());
        incidencias = procesarFiltros(incidencias,q,estado);
        incidencias =  servicioIncidencia.procesarPaginacion(incidencias,model,pageNum,perPage);
        model = cargarListar(model,incidencias);
        model.addAttribute("tituloPagina", "Listar Mis Incidencias Asignadas");
        return "tech/incidencia/listar";
    }

    public String listarResueltas(Model model, String q, String perPage, String pageNum, String estado) {
        Usuario usuario = seguridad.obtenerUsuarioLogado();
        List<Incidencia> incidencias = servicioIncidencia.getIncidenciasPorIdTecnico(usuario.getId());
        incidencias = incidencias.stream()
                .filter(i -> i.getEstado().getTipo() == TipoEstados.Final)
                .sorted(Comparator.comparing(Incidencia::getFechaModificacion).reversed())
                .toList();
        incidencias = procesarFiltros(incidencias,q,estado);
        incidencias =  servicioIncidencia.procesarPaginacion(incidencias,model,pageNum,perPage);
        model = cargarListar(model,incidencias);
        model.addAttribute("tituloPagina", "Listar Mis Incidencias Resueltas");
        return "tech/incidencia/listar";
    }

    public Model cargarListar(Model model,List<Incidencia> incidencias) {
        model.addAttribute("incidencias", incidencias.stream().map(IncidenciaTechDao_Listar::crearDao).toList());
        model.addAttribute("estadisticas", cargarEstadisticas());
        model.addAttribute("mostrarAsignar", false);
        model.addAttribute("estados", servicioEstado.findAll().stream().map(EstadoTechDao_Seleccionar::crearDao).toList());
        model.addAttribute("estadoNuevo",new EstadoTechDao_ModificarIncidencia());
        return model;
    }

    public List<Incidencia> procesarFiltros(List<Incidencia> incidencias, String q, String estado) {
        return incidencias.stream()
                .filter( i -> q != null ? !q.isEmpty() ? i.getTitulo().toLowerCase().contains(q.toLowerCase()) : true : true)
                .filter( i -> estado != null ? !estado.isEmpty() ? i.getEstado().getValor().equals(estado) : true : true)
                .toList();
    }

    public EstadisticasDao_Listar cargarEstadisticas() {
        Usuario usuario = seguridad.obtenerUsuarioLogado();
        int[] estadisticas = {0,0,0};
        List<Incidencia> incidencias = servicioIncidencia.getIncidenciasPorIdTecnico(usuario.getId());

        estadisticas[0] = incidencias.size();
        estadisticas[1] = incidencias.stream().filter(i -> i.getEstado().getTipo() != TipoEstados.Final).toList().size();
        estadisticas[2] = incidencias.stream().filter(i -> i.getEstado().getTipo() == TipoEstados.Final).toList().size();

        return EstadisticasDao_Listar.crearDao(estadisticas);
    }

    public String listarSinAsignar(Model model, String q, String perPage, String pageNum, String estado) {
        List<Incidencia> incidencias = servicioIncidencia.getIncidenciasSinAsignar();
        incidencias.sort(Comparator.comparing(Incidencia::getFechaModificacion).reversed());
        incidencias = procesarFiltros(incidencias,q,estado);
        incidencias =  servicioIncidencia.procesarPaginacion(incidencias,model,pageNum,perPage);
        model = cargarListar(model,incidencias);
        model.addAttribute("mostrarAsignar", true);
        model.addAttribute("tituloPagina", "Listar Incidencias Sin Asignadas");
        return "tech/incidencia/listar";
    }

    public String asignarIncidencia(Model model, RedirectAttributes redirectAttributes, long id) {
        try {
            Optional<Incidencia> incidencia = servicioIncidencia.findById(id);
            if (incidencia.isPresent()) {
                if (incidencia.get().getTecnico() != servicioUsuario.findByUsername("sin-tecnico").orElseThrow()) {
                    redirectAttributes.addFlashAttribute("error", "No puedes asignarte una tarea ya asignada.");
                } else {
                    incidencia.get().setTecnico(seguridad.obtenerUsuarioLogado());
                    incidencia.get().setFechaModificacion(LocalDateTime.now());
                    servicioIncidencia.save(incidencia.get());
                    redirectAttributes.addFlashAttribute("info", "Incidencia asignada con exito.");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "No existe una incidencia con ese id");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Algo salio mal durante la asignacion de incidencias.");
        }
        return "redirect:/tech/dashboard";

    }

    public String inspeccionarIncidencia(Model model, RedirectAttributes redirectAttributes, long id) {
        try {
            Optional<Incidencia> incidencia = servicioIncidencia.findById(id);
            if (incidencia.isPresent()) {
                if (incidencia.get().getTecnico().getId() != seguridad.obtenerUsuarioLogado().getId()) {
                    redirectAttributes.addFlashAttribute("error","No puedes inspeccionar una incidencia que no te pertenece.");
                } else {
                    model.addAttribute("incidencia", IncidenciaDao_Inspeccionar.crearDao(incidencia.get()));
                    model.addAttribute("estados", servicioEstado.findAll().stream().map(EstadoTechDao_Seleccionar::crearDao).toList());
                    return "tech/incidencia/inspeccionar";
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "No existe una incidencia con ese id");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Algo salio mal durante la asignacion de incidencias.");
        }
        return "redirect:/tech/dashboard";
    }

    public String eliminarIncidencia(Model model, RedirectAttributes redirectAttributes, long id) {
        try {
            Optional<Incidencia> incidencia = servicioIncidencia.findById(id);
            if (incidencia.isPresent()) {
                if (incidencia.get().getTecnico().getId() != seguridad.obtenerUsuarioLogado().getId()) {
                    throw new Exception("No puedes eliminar una incidencia que no te pertenece");
                } else {
                    if (!servicioIncidencia.eliminar(id)) {
                        throw new Exception("Algo ha salido mal durante la eliminacion de la incidencia.");
                    } else {
                        redirectAttributes.addFlashAttribute("info", "Incidencia eliminada con exito.");
                    }
                }
            } else {
                throw new Exception("Incidencia no encontrada");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",e.getMessage());
        }
        return "redirect:/tech/dashboard";
    }

    public String modificarIncidencia(Model model, RedirectAttributes redirectAttributes, EstadoTechDao_ModificarIncidencia estadoDao, long id) {
        try {
            Optional<Incidencia> incidencia = servicioIncidencia.findById(id);
            if (incidencia.isPresent()) {
                if (incidencia.get().getTecnico().getId() != seguridad.obtenerUsuarioLogado().getId()) {
                    redirectAttributes.addFlashAttribute("error","No puedes editar una incidencia que no te pertenece.");
                } else {
                    incidencia.get().setEstado(servicioEstado.findByValor(estadoDao.getValor()).orElseThrow());
                    incidencia.get().setFechaModificacion(LocalDateTime.now());
                    incidencia.get().setFechaIEA(LocalDateTime.now());
                    servicioIncidencia.save(incidencia.get());
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "No existe una incidencia con ese id");
            }
        } catch (Exception e) {
            System.out.println("error: " + e);
            redirectAttributes.addFlashAttribute("error", "Algo ha salido mal durante la modificacion de la incidencia.");
        }

        return "redirect:/tech/dashboard";
    }
}
