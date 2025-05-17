package com.salesianostriana.dam.GarciaMariaPablo.old.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorPrincipal2 {

    @GetMapping("/")
    public String cargarPaginaPrincipal() {
        return "admin/otros/paginaPrincipal";
    }

}
