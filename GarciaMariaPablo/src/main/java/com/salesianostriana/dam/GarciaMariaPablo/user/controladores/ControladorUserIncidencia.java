package com.salesianostriana.dam.GarciaMariaPablo.user.controladores;

import com.salesianostriana.dam.GarciaMariaPablo.user.daos.incidencia.IncidenciaUserDao_Crear;
import com.salesianostriana.dam.GarciaMariaPablo.user.daos.incidencia.IncidenciaUserDao_Modificar;
import com.salesianostriana.dam.GarciaMariaPablo.user.servicios.ServicioUserIncidencias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user/incidencias")
public class ControladorUserIncidencia {

    @Autowired
    ServicioUserIncidencias servicioUserIncidencias;

    @GetMapping("/inspeccionar/{id}")
    public String inspeccionar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return servicioUserIncidencias.inspeccionar(id,model, redirectAttributes);
    }

    @GetMapping("/crear")
    public String cargarCrear(Model model, RedirectAttributes redirectAttributes) {
        return servicioUserIncidencias.cargarCrear(model,redirectAttributes);
    }

    @GetMapping("/modificar/{id}")
    public String cargarModificar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return servicioUserIncidencias.cargarModificar(id,model,redirectAttributes);
    }

    @PutMapping
    public String modificar(@ModelAttribute IncidenciaUserDao_Modificar incidenciaDao, Model model, RedirectAttributes redirectAttributes) {
        return servicioUserIncidencias.modificar(incidenciaDao,model,redirectAttributes);
    }

    @PostMapping
    public String crear(Model model, RedirectAttributes redirectAttributes, @ModelAttribute IncidenciaUserDao_Crear incidenciaDao) {
        return servicioUserIncidencias.crear(model,redirectAttributes,incidenciaDao);
    }

    @GetMapping
    public String listar(Model model, RedirectAttributes redirectAttributes,
                         @RequestParam(name = "q", required = false) String q,
                         @RequestParam(name = "perPage", defaultValue = "10", required = false) String perPage,
                         @RequestParam(name = "pagina", defaultValue = "1", required = false) String pageNum,
                         @RequestParam(name = "estado", required = false) String estadoValue)
    {
        return servicioUserIncidencias.listar(model,redirectAttributes,q,perPage,pageNum,estadoValue);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable long id, RedirectAttributes redirectAttributes) {
        return servicioUserIncidencias.eliminar(id,redirectAttributes);
    }

}
