package com.salesianostriana.dam.GarciaMariaPablo.controladores;

import com.salesianostriana.dam.GarciaMariaPablo.servicios.ServicioEstado;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/estados")
public class ControladorEstado {

    @Autowired
    private ServicioEstado servicioEstado;

    public void activeTab(Model model, HttpServletRequest request) {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            model.addAttribute("activeTab", "estados");
        }
    }

    @GetMapping
    public String listar(Model model) {
        return null;
    };

    @GetMapping("/crear")
    public String cargarCrear(Model model) {
        return null;
    }

    @GetMapping("/{id}")
    public String cargarModificar(Model model) {
        return null;
    }

    @PostMapping
    public String crear(Model model) {
        return null;
    }

    @PutMapping
    public String modificar(Model model) {
        return null;
    }

    @DeleteMapping
    public String eliminar(Model model) {
        return null;
    }

}
