package com.salesianostriana.dam.GarciaMariaPablo.global.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.global.daos.otros.ContrasenaDao_Modificar;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.otros.EstadisticasDao;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.usuario.UsuarioDao_LogIn;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.usuario.UsuarioDao_Modificar;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.usuario.UsuarioDao_Register;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Incidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.seguridad.CodificadorContrasenas;
import com.salesianostriana.dam.GarciaMariaPablo.global.seguridad.ServicioSeguridad;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ServicioPrincipal {

    @Autowired
    private ServicioSeguridad seguridad;
    @Autowired
    private ServicioIncidencia servicioIncidencia;
    @Autowired
    private ServicioUsuario servicioUsuario;

    public Usuario revertirDao(UsuarioDao_Modificar usuarioDao) {
        Usuario original = servicioUsuario.findById(usuarioDao.getId()).orElseThrow();
        return Usuario.builder()
                .id(usuarioDao.getId())
                .nombre(usuarioDao.getNombre().isEmpty() ? original.getNombre() : usuarioDao.getNombre())
                .email(usuarioDao.getEmail().isEmpty() ? original.getEmail() : usuarioDao.getEmail())
                .telefono(usuarioDao.getTelefono().isEmpty() ? original.getTelefono() : usuarioDao.getTelefono())
                .apellidos(usuarioDao.getApellidos().isEmpty() ? original.getApellidos() : usuarioDao.getApellidos())
                .rol(original.getRol())
                .fechaRegistro(original.getFechaRegistro())
                .username(original.getUsername())
                .password(original.getPassword())
                .editable(original.isEditable())
                .build();

    }

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
        model.addAttribute("usuario", usuario);

        switch (usuario.getRol()) {
            case TECH -> {
                List<Incidencia> incidencias = servicioIncidencia.getIncidenciasResueltasPorIdTecnico(usuario.getId());
                long horasMedias=0;

                for (Incidencia incidencia : incidencias) {
                    horasMedias += ChronoUnit.HOURS.between(incidencia.getFechaCreacion(), incidencia.getFechaIEA());
                }

                horasMedias = horasMedias/incidencias.size();

                model.addAttribute("estadisticas", new EstadisticasDao(incidencias
                        .stream()
                        .filter(i -> i.getFechaIEA().isAfter(LocalDateTime.now().minusDays(30)) && i.getFechaIEA().isBefore(LocalDateTime.now()))
                        .toList()
                        .size(),horasMedias));
            }
            case ADMIN -> {
                List<Incidencia> incidencias = servicioIncidencia.getIncidenciasResueltas()
                        .stream()
                        .filter(i -> i.getFechaIEA().isAfter(LocalDateTime.now().minusDays(30)) && i.getFechaIEA().isBefore(LocalDateTime.now()))
                        .toList();
                long horasMedias=0;

                for (Incidencia incidencia : incidencias) {
                    horasMedias += ChronoUnit.HOURS.between(incidencia.getFechaCreacion(), incidencia.getFechaIEA());
                }


                horasMedias = !incidencias.isEmpty() ? horasMedias / incidencias.size() : 0;

                model.addAttribute("estadisticas", new EstadisticasDao(incidencias.size(),horasMedias));
            }
        }
        model.addAttribute("usuarioDao", UsuarioDao_Modificar.crearDao(usuario));
        model.addAttribute("contrasena",new ContrasenaDao_Modificar());
        return "global/perfil";
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

    public String modificarContrasena(ContrasenaDao_Modificar contrasenaDaoModificar, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = seguridad.obtenerUsuarioLogado();

        if (!CodificadorContrasenas.passwordEncoder().matches(contrasenaDaoModificar.getActual(),usuario.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Contraseña actual incorrecta.");
            return "redirect:/perfil";
        }
        if (!contrasenaDaoModificar.getNueva().equals(contrasenaDaoModificar.getConfirmar())) {
            redirectAttributes.addFlashAttribute("error","Las contraseñas no coinciden.");
            return "redirect:/perfil";
        }
        usuario.setPassword(CodificadorContrasenas.passwordEncoder().encode(contrasenaDaoModificar.getNueva()));
        servicioUsuario.save(usuario);
        redirectAttributes.addFlashAttribute("info","Tus datos de usuarios han sido modificados por ende se solicita que vuelvas a iniciar sesion.");
        return "redirect:/logout";
    }

    public String modificarUsuario(UsuarioDao_Modificar usuarioDao, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {

        if (servicioUsuario.findByEmail(usuarioDao.getEmail()) != null) {
            redirectAttributes.addFlashAttribute("error", "El email indicado ya esta en uso.");
            return "redirect:/perfil";
        }

        servicioUsuario.edit(revertirDao(usuarioDao));
        redirectAttributes.addFlashAttribute("info","Tus datos de usuarios han sido modificados por ende se solicita que vuelvas a iniciar sesion.");
        seguridad.forzarCerrarSesion(request);
        return "redirect:/login";
    }
}
