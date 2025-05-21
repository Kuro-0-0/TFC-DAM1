package com.salesianostriana.dam.GarciaMariaPablo.global.controladores;

import com.salesianostriana.dam.GarciaMariaPablo.global.daos.otros.ContrasenaDao_Modificar;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.otros.EmailDao_Contactar;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.otros.EmailDao_Contratar;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.usuario.UsuarioDao_LogIn;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.usuario.UsuarioDao_Modificar;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.usuario.UsuarioDao_Register;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.ServicioPrincipal;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class ControladorPrincipal {

    @Autowired
    private ServicioPrincipal servicioPrincipal;



    @PutMapping("/contratar")
    public String mail(Model model, RedirectAttributes redirectAttributes, @ModelAttribute EmailDao_Contratar emailDao) throws MessagingException, IOException {
        return servicioPrincipal.enviarMail(model,redirectAttributes,emailDao);
    }

    @GetMapping("/login")
    public String cargarLogIn(Model model, RedirectAttributes redirectAttributes) {
        return servicioPrincipal.cargarLogIn(model,redirectAttributes);
    }

    @PostMapping("/login")
    public String logIn(@ModelAttribute UsuarioDao_LogIn usuarioDao, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        return servicioPrincipal.logIn(usuarioDao,model, redirectAttributes, request);
    }

    @GetMapping("/register")
    public String cargarRegister(Model model, RedirectAttributes redirectAttributes) {
        return servicioPrincipal.cargarRegister(model, redirectAttributes);
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

    @PutMapping("/perfil/password")
    public String modificarContrasena(@ModelAttribute ContrasenaDao_Modificar contrasenaDao_Modificar, Model model, RedirectAttributes redirectAttributes) {
        return servicioPrincipal.modificarContrasena(contrasenaDao_Modificar, model, redirectAttributes);
    }

    @PutMapping("/perfil/modificar")
    public String modificarUsuario(@ModelAttribute UsuarioDao_Modificar usuarioDao, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        return servicioPrincipal.modificarUsuario(usuarioDao,model,redirectAttributes, request);
    }

    @GetMapping("/nosotros")
    public String cargarNosotros(Model model) {return servicioPrincipal.cargarNosotros(model);}

    @GetMapping("/contacto")
    public String cargarContacto(Model model) {return servicioPrincipal.cargarContacto(model);}

    @PutMapping("/contacto/submit")
    public String responderFormulario(Model model, RedirectAttributes redirectAttributes, @ModelAttribute EmailDao_Contactar emailDao) throws MessagingException {
        return servicioPrincipal.enviarContacto(model,redirectAttributes,emailDao);
    }

    @GetMapping("/politica-privacidad")
    public String cargarPPrivacidad(Model model) {return servicioPrincipal.cargarPPrivacidad(model);}

    @GetMapping("/faq")
    public String cargarFAQ(Model model) {return servicioPrincipal.cargarFAQ(model);}

    @GetMapping()
    public String cargarIndex(Model model, RedirectAttributes redirectAttributes) {
        return servicioPrincipal.cargarIndex(model, redirectAttributes);
    }

}
