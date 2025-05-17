package com.salesianostriana.dam.GarciaMariaPablo.admin.servicios;


import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.otros.RolDao_ListarUsuarios;
import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.usuario.UsuarioDao_Crear;
import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.usuario.UsuarioDao_Listar;
import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.usuario.UsuarioDao_Modificar;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.RolesUsuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.repositorios.RepositorioUsuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.ServicioUsuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.otros.base.ServicioBaseImpl;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioAdminUsuario extends ServicioBaseImpl<Usuario, Long, RepositorioUsuario> {

    private final ServicioUsuario servicioUsuario;

    public ServicioAdminUsuario(ServicioUsuario servicioUsuario) {
        super();
        this.servicioUsuario = servicioUsuario;
    }

    public Usuario revertirDao(UsuarioDao_Modificar usuarioDao) {
        String password = usuarioDao.getPassword().isEmpty() ? repositorio.findPasswordById(usuarioDao.getId()).orElseThrow() : usuarioDao.getPassword();
        return Usuario.builder()
                .id(usuarioDao.getId())
                .apellidos(usuarioDao.getApellidos())
                .rol(usuarioDao.getRol())
                .nombre(usuarioDao.getNombre())
                .username(usuarioDao.getUsername())
                //                .incidenciasReportadas(usuario.getIncidenciasReportadas())
                //                .incidenciasGestionadas(usuario.getIncidenciasGestionadas())
                .password(password)
                .editable(true)
                .build();
    }

    public String listar(Model model, String paginaNum, String perPageNum, String ordenPor, boolean ordenAsc, String filtroUsername, String filtroNombre, String filtroApellidos, List<String> rolesName, String mostrarOcultos) {
        List<Long> roles = null;
        List<Usuario> usuarios;
        List<RolDao_ListarUsuarios> convertedRol = new ArrayList<>();

        for (RolesUsuario rol : RolesUsuario.values()) {
            convertedRol.add(RolDao_ListarUsuarios.crearDao(rol, false));
        }

        if (rolesName != null && !rolesName.isEmpty()) {
            List<String> rolesLC = rolesName.stream().map(String::toLowerCase).toList();
            convertedRol.forEach(rol -> rol.setSelected(rolesLC.contains(rol.getName().toLowerCase())));
        }


        usuarios = repositorio.findByFilters(filtroUsername, filtroNombre, filtroApellidos, mostrarOcultos.equals("on"), rolesName);
        usuarios = procesarOrden(usuarios, ordenPor, ordenAsc);
        usuarios = procesarPaginacion(usuarios, model, paginaNum, perPageNum);

        model.addAttribute("filtroUsername", filtroUsername);
        model.addAttribute("filtroNombre", filtroNombre);
        model.addAttribute("filtroApellidos", filtroApellidos);

        model.addAttribute("mostrandoAhora", usuarios.size());
        model.addAttribute("roles", convertedRol);
        model.addAttribute("mostrarOcultos", mostrarOcultos.equals("on"));
        model.addAttribute("usuarios", usuarios.stream().map(UsuarioDao_Listar::crearDao).toList());

        return "admin/usuario/listar";
    }

    private List<Usuario> procesarOrden(List<Usuario> usuarios, String ordenPor, boolean ordenAsc) {

        if (ordenPor != null) {
            Comparator<Usuario> comparator;
            switch (ordenPor) {
                case "nombre" -> comparator = Comparator.comparing(Usuario::getNombre);
                case "apellidos" -> comparator = Comparator.comparing(Usuario::getApellidos);
                case "rol" -> comparator = Comparator.comparing(Usuario::getRol);
                default -> comparator = Comparator.comparing(Usuario::getUsername);
            }
            if (!ordenAsc) {
                comparator = comparator.reversed();
            }
            usuarios.sort(comparator);
        }

        return usuarios;

    }

    public String cargarCrear(Model model) {
        if (!model.containsAttribute("usuarioDao")) {
            model.addAttribute("usuarioDao", new UsuarioDao_Crear());
        }
        model.addAttribute("roles", RolesUsuario.values());
        model.addAttribute("modificar", false);
        return "admin/usuario/formulario";
    }

    public String cargarModificar(Model model, long id, RedirectAttributes redirectAttributes) {
        Usuario objetivo = findById(id).orElseThrow();
        if (!objetivo.isEditable()) {
            redirectAttributes.addFlashAttribute("error", "No puedes modificar el usuario seleccionado.");
            return "redirect:/admin/usuarios";
        }

        if (!model.containsAttribute("usuarioDao")) {
            model.addAttribute("usuarioDao", UsuarioDao_Modificar.crearDao(objetivo));
        }

        model.addAttribute("roles", RolesUsuario.values());
        model.addAttribute("modificar", true);
        return "admin/usuario/formulario";
    }

    public String eliminar(long id, RedirectAttributes redirectAttributes) {
        Usuario objetivo = findById(id).orElseThrow();
        Optional<Usuario> userDefault;
        if (!objetivo.isEditable()) {
            redirectAttributes.addFlashAttribute("error", "No puedes eliminar el usuario seleccionado.");
            return "redirect:/admin/usuarios"; // Con esto evitamos que se editen los sin-tecnico/sin-reportante
        }

        if (objetivo.getRol().equals(RolesUsuario.TECH)) {
            userDefault = repositorio.findByUsername("sin-tecnico");
        } else if (objetivo.getRol().equals(RolesUsuario.USER)) {
            userDefault = repositorio.findByUsername("sin-reportante");
        } else {
            redirectAttributes.addFlashAttribute("error", "No puedes eliminar administradores del sistema.");
            return "redirect:/admin/usuarios";
        }

        objetivo.transferirIncidencias(userDefault.orElseThrow());
        delete(objetivo);
        return "redirect:/admin/usuarios";
    }

    public String modificar(UsuarioDao_Modificar usuarioDao, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuarioAntiguo = findById(usuarioDao.getId()).orElseThrow();
        Usuario nuevoUsuario = revertirDao(usuarioDao);
        if (nuevoUsuario != null) {
            if (repositorio.findByUsername(nuevoUsuario.getUsername()).isPresent() && !usuarioAntiguo.getUsername().equals(nuevoUsuario.getUsername())) {
                model.addAttribute("usuarioDao", usuarioDao);
                model.addAttribute("error", "El nombre de usuario ya existe.");
                return cargarModificar(model, usuarioDao.getId(), redirectAttributes);
            }
            if (nuevoUsuario.getRol() == null) {
                model.addAttribute("usuarioDao", usuarioDao);
                model.addAttribute("error", "Debe escoger un rol.");
                return cargarModificar(model, usuarioDao.getId(), redirectAttributes);
            }
        }

        edit(nuevoUsuario);
        return "redirect:/admin/usuarios";
    }

    public String crear(UsuarioDao_Crear usuarioDao, Model model) {
        Usuario nuevoUsuario = usuarioDao.revertirDao();
        if (nuevoUsuario != null) {
            if (repositorio.findByUsername(nuevoUsuario.getUsername()).isPresent()) {
                model.addAttribute("usuarioDao", usuarioDao);
                model.addAttribute("error", "El nombre de usuario ya existe.");
                return cargarCrear(model);
            }
            if (nuevoUsuario.getRol() == null) {
                model.addAttribute("usuarioDao", usuarioDao);
                model.addAttribute("error", "Debe escoger un rol.");
                return cargarCrear(model);
            }
            save(servicioUsuario.crearUsuario(nuevoUsuario));
        }
        return "redirect:/admin/usuarios";
    }
}
