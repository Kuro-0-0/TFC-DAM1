package com.salesianostriana.dam.GarciaMariaPablo.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorPrincipal {

    @GetMapping("/")
    public String cargarPaginaPrincipal() {
        return "admin/paginaPrincipal";
    }

}
