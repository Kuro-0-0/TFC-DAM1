package com.salesianostriana.dam.GarciaMariaPablo.global.seguridad;

import com.salesianostriana.dam.GarciaMariaPablo.global.daos.usuario.UsuarioDao_LogIn;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.usuario.UsuarioDao_Register;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.ServicioUsuario;
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

import java.util.Optional;

@Service
public class ServicioSeguridad {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ServicioUsuario servicioUsuario;

    public Usuario obtenerUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Usuario) {
            return (Usuario) principal;
        }
        return null;
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

    public String cargarLogIn(Model model) {
        if (obtenerUsuarioLogado() != null) {
            return "redirect:/dashboard";
        }
        if (!model.containsAttribute("usuarioDao")) {
            model.addAttribute("usuarioDao", new UsuarioDao_LogIn());
        }
        return "global/login";
    }

    public String cargarRegister(Model model) {

        if (obtenerUsuarioLogado() != null) {
            return "redirect:/dashboard";
        }

        model.addAttribute("usuarioDao", new UsuarioDao_Register());
        return "global/register";
    }

    public String register(UsuarioDao_Register usuarioDao, Model model, HttpServletRequest request) {
        Usuario nuevoUsuario;
        Optional<Usuario> usuarioExistente = servicioUsuario.findByUsername(usuarioDao.getUsername());

        if (usuarioExistente.isPresent()) {
            model.addAttribute("error", "El usuario ya existe");
            model.addAttribute("usuarioDao", usuarioDao);
            return "global/register";
        }

        if (!usuarioDao.getPassword().equals(usuarioDao.getPassword2())) {
            model.addAttribute("error", "Las contraseñas no coinciden.");
            model.addAttribute("usuarioDao", usuarioDao);
            return "global/register";
        }

        nuevoUsuario = servicioUsuario.save(servicioUsuario.crearUsuario(usuarioDao.revertirDao()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                nuevoUsuario, nuevoUsuario.getPassword(), nuevoUsuario.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());


        return "redirect:/dashboard";
    }
}
