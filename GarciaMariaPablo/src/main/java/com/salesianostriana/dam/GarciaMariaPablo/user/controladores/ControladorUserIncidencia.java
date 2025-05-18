package com.salesianostriana.dam.GarciaMariaPablo.user.controladores;

import com.salesianostriana.dam.GarciaMariaPablo.user.servicios.ServicioUserIncidencias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    public String listar(Model model, RedirectAttributes redirectAttributes,
                         @RequestParam(name = "q", required = false) String q,
                         @RequestParam(name = "perPage", defaultValue = "10", required = false) String perPage,
                         @RequestParam(name = "pagina", defaultValue = "1", required = false) String pageNum,
                         @RequestParam(name = "estado", required = false) String estadoValue)
    {
        return servicioUserIncidencias.listar(model,redirectAttributes,q,perPage,pageNum,estadoValue);
    }

}
