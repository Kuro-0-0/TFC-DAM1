package com.salesianostriana.dam.GarciaMariaPablo.tech.controladores;

import com.salesianostriana.dam.GarciaMariaPablo.tech.servicios.ServicioTechPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"/tech","/TECH"})
public class ControladorTechPrincipal {

    @Autowired
    private ServicioTechPrincipal servicioTechPrincipal;

    @GetMapping("/dashboard")
    public String cargarDashboard(Model model, RedirectAttributes redirectAttributes,
                                  @RequestParam(name ="recienteLimit",defaultValue = "3",required = false) int incidenciasRecientes) {
        return servicioTechPrincipal.cargarDashboard(model,redirectAttributes,incidenciasRecientes);
    }


}
