package com.salesianostriana.dam.GarciaMariaPablo.global.controladores;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.ServicioUsuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BORRAR_AUTOLOGIN {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ServicioUsuario servicioUsuario;

    @GetMapping("/autologin")
    public String autologin(HttpServletRequest request) {
        String username = "admin";
        String password = "admin";

        Usuario user = servicioUsuario.findByUsername(username).orElseThrow();

        UsernamePasswordAuthenticationToken authReq =
                new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());

        Authentication auth = authenticationManager.authenticate(authReq);

        SecurityContextHolder.getContext().setAuthentication(auth);

        // Vincula el contexto de seguridad a la sesión HTTP
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        return "redirect:/admin"; // Redirige a la página deseada
    }

    @GetMapping("/error/403")
    public String error403() {
        return "error/403"; // Verifica si carga desde navegador
    }

}
