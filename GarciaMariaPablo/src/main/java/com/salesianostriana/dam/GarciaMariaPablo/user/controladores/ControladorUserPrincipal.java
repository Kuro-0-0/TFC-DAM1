package com.salesianostriana.dam.GarciaMariaPablo.user.controladores;

import com.salesianostriana.dam.GarciaMariaPablo.user.servicios.ServicioUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"/user","/USER"})
public class ControladorUserPrincipal {

    @Autowired
    private ServicioUserPrincipal servicioUserPrincipal;

    @GetMapping("/dashboard")
    public String cargarDashboard(Model model, RedirectAttributes redirectAttributes,
        @RequestParam(name ="recienteLimit",defaultValue = "3",required = false) int incidenciasRecientes) {
        return servicioUserPrincipal.cargarDashboard(model,redirectAttributes,incidenciasRecientes);
    }

}
