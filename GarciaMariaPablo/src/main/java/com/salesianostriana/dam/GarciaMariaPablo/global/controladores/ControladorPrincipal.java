package com.salesianostriana.dam.GarciaMariaPablo.global.controladores;

import com.salesianostriana.dam.GarciaMariaPablo.global.daos.usuario.UsuarioDao_LogIn;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.ServicioPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ControladorPrincipal {

    @Autowired
    private ServicioPrincipal servicioPrincipal;

    @GetMapping("/login")
    public String cargarLogIn(Model model) {
        return servicioPrincipal.cargarLogIn(model);
    }

    @PostMapping("/login")
    public String logIn(@ModelAttribute UsuarioDao_LogIn usuarioDao, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        return servicioPrincipal.logIn(usuarioDao,model, redirectAttributes, request);
    }

    @GetMapping("/dashboard")
    public String redirDashboard(Model model) {
        return servicioPrincipal.redirDashboard(model);
    }

    @GetMapping("/perfil")
    public String cargarPerfil(Model model, @AuthenticationPrincipal Usuario usuario) {
        return servicioPrincipal.cargarPerfil(model, usuario);
    }

    @GetMapping("/nosotros")
    public String cargarNosotros(Model model) {return servicioPrincipal.cargarNosotros(model);}

    @GetMapping("/contacto")
    public String cargarContacto(Model model) {return servicioPrincipal.cargarContacto(model);}

    @GetMapping("/politica-privacidad")
    public String cargarPPrivacidad(Model model) {return servicioPrincipal.cargarPPrivacidad(model);}

    @GetMapping("/faq")
    public String cargarFAQ(Model model) {return servicioPrincipal.cargarFAQ(model);}

    @GetMapping()
    public String cargarIndex(Model model, @AuthenticationPrincipal Usuario usuario) {
        return servicioPrincipal.cargarIndex(model, usuario);
    }

}
