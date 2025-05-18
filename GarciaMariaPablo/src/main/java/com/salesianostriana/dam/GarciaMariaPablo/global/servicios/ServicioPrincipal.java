package com.salesianostriana.dam.GarciaMariaPablo.global.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.global.daos.usuario.UsuarioDao_LogIn;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.usuario.UsuarioDao_Register;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.seguridad.ServicioSeguridad;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class ServicioPrincipal {

    @Autowired
    private ServicioSeguridad seguridad;

    public String cargarIndex(Model model) {
        return "global/index";
    }

    public String cargarNosotros(Model model) {
        return "global/quienes-somos";
    }

    public String cargarPPrivacidad(Model model) {
        return "global/politica-privacidad";
    }

    public String cargarContacto(Model model) {
        return "global/contacto";
    }

    public String cargarFAQ(Model model) {
        return "global/faq";
    }
    
    public String cargarPerfil(Model model) {
        Usuario usuario = seguridad.obtenerUsuarioLogado();
        System.out.println(usuario);
        return "redirect:/";
    }

    public String cargarLogIn(Model model,RedirectAttributes redirectAttributes) {
        if (seguridad.obtenerUsuarioLogado() != null) {
            redirectAttributes.addFlashAttribute("info", "No  puedes Iniciar sesion en un perfil con una sesion ya iniciada.");
            return "redirect:/";
        }
        return  seguridad.cargarLogIn(model);
    }

    public String logIn(UsuarioDao_LogIn usuarioDao, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        return seguridad.logIn(usuarioDao, model, redirectAttributes, request);
    }

    public String redirDashboard(Model model) {
        Usuario usuario = seguridad.obtenerUsuarioLogado();
        if (usuario != null) {
            return "redirect:" + usuario.getRol().name().toLowerCase() + "/dashboard";
        }
        return "redirect:/";
    }

    public String cargarRegister(Model model,RedirectAttributes redirectAttributes) {
        if (seguridad.obtenerUsuarioLogado() != null) {
            redirectAttributes.addFlashAttribute("info", "No  puedes registrar un nuevo perfil con una sesion iniciada.");
            return "redirect:/";
        }
        return seguridad.cargarRegister(model);
    }

    public String register(UsuarioDao_Register usuarioDao,Model model, HttpServletRequest request) {
        return seguridad.register(usuarioDao,model,request);
    }
}
