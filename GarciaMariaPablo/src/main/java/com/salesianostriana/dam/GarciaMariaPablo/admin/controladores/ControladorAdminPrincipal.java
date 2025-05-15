package com.salesianostriana.dam.GarciaMariaPablo.admin.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/admin","/ADMIN"})
public class ControladorAdminPrincipal {

    @GetMapping("/dashboard")
    public String cargarDashboard() {
        return "admin/otros/paginaPrincipal";
    }

}
