package com.salesianostriana.dam.GarciaMariaPablo.tech.controladores;

import com.salesianostriana.dam.GarciaMariaPablo.tech.daos.estado.external.EstadoTechDao_ModificarIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.tech.servicios.ServicioTechIncidencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tech/incidencias")
public class ControladorTechIncidencia {

    @Autowired
    private ServicioTechIncidencia servicioTechIncidencia;

    @GetMapping("/asignadas")
    public String listarAsignadas(Model model,
                                  @RequestParam(name = "q", required = false) String q,
                                  @RequestParam(name = "perPage", defaultValue = "10", required = false) String perPage,
                                  @RequestParam(name = "pagina", defaultValue = "1", required = false) String pageNum,
                                  @RequestParam(name = "estado", defaultValue = "", required = false) String estado
                                  ) {
        return servicioTechIncidencia.listarAsignadas(model, q, perPage, pageNum,estado);
    }

    @GetMapping("/resueltas")
    public String listarResueltas(Model model,
                                  @RequestParam(name = "q", required = false) String q,
                                  @RequestParam(name = "perPage", defaultValue = "10", required = false) String perPage,
                                  @RequestParam(name = "pagina", defaultValue = "1", required = false) String pageNum,
                                  @RequestParam(name = "estado", defaultValue = "", required = false) String estado
                                  ) {
        return servicioTechIncidencia.listarResueltas(model, q, perPage, pageNum,estado);
    }

    @GetMapping("/sin-asignar")
    public String listarSinAsignar(Model model,
                                  @RequestParam(name = "q", required = false) String q,
                                  @RequestParam(name = "perPage", defaultValue = "10", required = false) String perPage,
                                  @RequestParam(name = "pagina", defaultValue = "1", required = false) String pageNum,
                                  @RequestParam(name = "estado", defaultValue = "", required = false) String estado
    ) {
        return servicioTechIncidencia.listarSinAsignar(model, q, perPage, pageNum,estado);
    }

    @PutMapping("/asignar/{id}")
    public String asignarIncidencia(Model model, RedirectAttributes redirectAttributes, @PathVariable long id) {
        return servicioTechIncidencia.asignarIncidencia(model,redirectAttributes,id);
    }

    @GetMapping("/inspeccionar/{id}")
    public String inspeccionarIncidencia(Model model, RedirectAttributes redirectAttributes, @PathVariable long id) {
        return servicioTechIncidencia.inspeccionarIncidencia(model,redirectAttributes,id);
    }

    @DeleteMapping("/incidencias/{id}")
    public String eliminarIncidencia(Model model, RedirectAttributes redirectAttributes, @PathVariable long id) {
        return servicioTechIncidencia.eliminarIncidencia(model,redirectAttributes,id);
    }

    @PutMapping("/modificar/{id}")
    public String modificarIncidencia(Model model, RedirectAttributes redirectAttributes, @ModelAttribute EstadoTechDao_ModificarIncidencia estadoDao, @PathVariable long id) {
        return servicioTechIncidencia.modificarIncidencia(model,redirectAttributes,estadoDao,id);
    }



}
