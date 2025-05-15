package com.salesianostriana.dam.GarciaMariaPablo.global.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.global.daos.usuario.UsuarioDao_LogIn;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class ServicioPrincipal {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ServicioUsuario servicioUsuario;

    public String cargarLogIn(Model model) {
        if (!model.containsAttribute("usuarioDao")) {
            model.addAttribute("usuarioDao", new UsuarioDao_LogIn());
        }
        return "global/login";
    }

    public String cargarIndex(Model model, Usuario usuario) {
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
        }
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

    public String redirDashboard(Model model) {
        System.out.println("AAAAAAAAAA"); // ESTO EN UN FUTURO TE REDIRIGIRA A TU DASHBOARD DEPENDIENDO DE TU ROL.
        return "redirect:/";
    }

    public String logIn(UsuarioDao_LogIn usuarioDao, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        try {
            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(usuarioDao.getUsername(), usuarioDao.getPassword());

            Authentication authentication = authenticationManager.authenticate(authRequest);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());

            return "redirect:/dashboard";

        } catch (AuthenticationException ex) {
            if (servicioUsuario.findByUsername(usuarioDao.getUsername()).isPresent()) {
                model.addAttribute("error", ex.getMessage());
            } else {
                model.addAttribute("error", "No existe ningun usuario con el nombre '" + usuarioDao.getUsername() + "'");
            }
            model.addAttribute("usuarioDao", usuarioDao);
            return cargarLogIn(model);
        }
    }

    public String cargarPerfil(Model model, Usuario usuario) {
        System.out.println(usuario);
        return "redirect:/";
    }
}
