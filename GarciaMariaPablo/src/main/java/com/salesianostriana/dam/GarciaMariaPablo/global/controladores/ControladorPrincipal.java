package com.salesianostriana.dam.GarciaMariaPablo.global.controladores;

import com.salesianostriana.dam.GarciaMariaPablo.global.daos.usuario.UsuarioDao_LogIn;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.usuario.UsuarioDao_Register;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.ServicioPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/register")
    public String cargarRegister(Model model) {
        return servicioPrincipal.cargarRegister(model);
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UsuarioDao_Register usuarioDao, Model model, HttpServletRequest request) {
        return servicioPrincipal.register(usuarioDao, model, request);
    }

    @GetMapping("/dashboard")
    public String redirDashboard(Model model) {
        return servicioPrincipal.redirDashboard(model);
    }

    @GetMapping("/perfil")
    public String cargarPerfil(Model model) {
        return servicioPrincipal.cargarPerfil(model);
    }

    @GetMapping("/nosotros")
    public String cargarNosotros(Model model) {return servicioPrincipal.cargarNosotros(model);}

    @GetMapping("/contacto")
    public String cargarContacto(Model model) {return servicioPrincipal.cargarContacto(model);}

    @GetMapping("/contacto/submit")
    public String responderFormulario(Model model,RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("info", "Tu mensaje ha sido enviado. En menos de 24 horas, nuestro equipo se pondrá en contacto con usted.");
        return "redirect:/";
    }

    @GetMapping("/politica-privacidad")
    public String cargarPPrivacidad(Model model) {return servicioPrincipal.cargarPPrivacidad(model);}

    @GetMapping("/faq")
    public String cargarFAQ(Model model) {return servicioPrincipal.cargarFAQ(model);}

    @GetMapping()
    public String cargarIndex(Model model) {
        return servicioPrincipal.cargarIndex(model);
    }

}
