package com.salesianostriana.dam.GarciaMariaPablo.old.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ControladorPrincipal2 {

    @GetMapping("/")
    public String cargarPaginaPrincipal() {
        return "admin/otros/paginaPrincipal";
    }

    @GetMapping("/contacto/submit")
    public String responderFormulario(Model model,RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("info", "Tu incidencia ha sido reportada. En menos de 24 horas, nuestro equipo se pondrá en contacto con usted.");
		return "redirect:/";
	}

}
