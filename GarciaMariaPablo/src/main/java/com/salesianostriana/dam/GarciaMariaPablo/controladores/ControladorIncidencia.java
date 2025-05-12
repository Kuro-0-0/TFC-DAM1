package com.salesianostriana.dam.GarciaMariaPablo.controladores;

import com.salesianostriana.dam.GarciaMariaPablo.daos.incidencia.IncidenciaDao_Crear;
import com.salesianostriana.dam.GarciaMariaPablo.daos.incidencia.IncidenciaDao_Modificar;
import com.salesianostriana.dam.GarciaMariaPablo.servicios.ServicioIncidencia;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/incidencias")
public class ControladorIncidencia {

    @Autowired
    private ServicioIncidencia servicioIncidencia;

    public void activeTab(Model model, HttpServletRequest request) {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            model.addAttribute("activeTab", "incidencias");
        }
    }

    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false, name = "pagina", defaultValue = "1") String paginaStr,
                         @RequestParam(required = false, name = "perPage", defaultValue = "10") String perPageStr,
                         @RequestParam(required = false, name = "ordenarPor", defaultValue = "id") String ordenPor,
                         @RequestParam(required = false, name = "ordenAsc", defaultValue = "true") Boolean ordenAsc,
                         @RequestParam(required = false, name = "reportantesSeleccionados") List<String> reportantes,
                         @RequestParam(required = false, name = "estadosSeleccionados") List<String> estados,
                         @RequestParam(required = false, name = "filtroTitulo", defaultValue = "") String filtroTitulo,
                         @RequestParam(required = false, name = "filtroUbicacion", defaultValue = "") String filtroUbicacion,
                         @RequestParam(required = false, name = "mostrarDesactivados", defaultValue = "") String mostrarDesactivados
    ) {
        return servicioIncidencia.listar(model,paginaStr,perPageStr,ordenPor,ordenAsc,reportantes,estados,filtroTitulo,filtroUbicacion,mostrarDesactivados);
    }

    @GetMapping("/estadisticas")
    public String cargarEstadisticas(Model model) {
        return servicioIncidencia.cargarEstadisticas(model);
    }

    @GetMapping("/crear")
    public String cargarCrear(Model model) {
        return servicioIncidencia.cargarCrear(model);
    }

    @GetMapping("/inspeccionar/{id}")
    public String inspeccionar(Model model, @PathVariable int id) {
        return servicioIncidencia.inspeccionar(model,id);
    }

    @GetMapping("/modificar/{id}")
    public String cargarModificar(Model model, @PathVariable long id) {
        return servicioIncidencia.cargarModificar(model, id);
    }

    @PostMapping
    public String crear(@ModelAttribute("IncidenciaDAO") IncidenciaDao_Crear incidenciaDao) {
        return servicioIncidencia.crear(incidenciaDao);
    }

    @PutMapping
    public String modificar(@ModelAttribute("IncidenciaDAO") IncidenciaDao_Modificar incidenciaDao) {
        return servicioIncidencia.modificar(incidenciaDao);
    }

    @DeleteMapping("{id}")
    public String eliminar(@PathVariable long id) {
        return servicioIncidencia.eliminar(id);
    }

}
