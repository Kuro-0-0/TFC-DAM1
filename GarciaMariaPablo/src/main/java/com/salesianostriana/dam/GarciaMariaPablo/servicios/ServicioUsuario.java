package com.salesianostriana.dam.GarciaMariaPablo.servicios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.salesianostriana.dam.GarciaMariaPablo.daos.otros.RolDao_ListarUsuarios;
import com.salesianostriana.dam.GarciaMariaPablo.daos.usuario.UsuarioDao_Crear;
import com.salesianostriana.dam.GarciaMariaPablo.daos.usuario.UsuarioDao_Listar;
import com.salesianostriana.dam.GarciaMariaPablo.daos.usuario.UsuarioDao_Modificar;
import com.salesianostriana.dam.GarciaMariaPablo.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.modelos.utilidades.Rol;
import com.salesianostriana.dam.GarciaMariaPablo.repositorios.RepositorioUsuario;
import com.salesianostriana.dam.GarciaMariaPablo.servicios.base.ServicioBaseImpl;

@Service
public class ServicioUsuario extends ServicioBaseImpl<Usuario, Long, RepositorioUsuario> {

    public Usuario revertirDao(UsuarioDao_Modificar usuarioDao, Usuario original) {
        String password = usuarioDao.getPassword().isEmpty() ? original.getPassword() : usuarioDao.getPassword();

        original.setUsername(usuarioDao.getUsername());
        original.setPassword(password);
        original.setNombre(usuarioDao.getNombre());
        original.setApellidos(usuarioDao.getApellidos());

        if (usuarioDao.getRol() != original.getRol()) {
            if (original.getRol() == Rol.tecnico) {
                original.transferirIncidencias(repositorio.findByUsername("sin-tecnico"));
            } else if (original.getRol() == Rol.reportante) {
                original.transferirIncidencias(repositorio.findByUsername("sin-reportante"));
            }

            original.setRol(usuarioDao.getRol());

        }

        return original;

    }

    public String listar(Model model, String paginaNum, String perPageNum, String ordenPor, boolean ordenAsc, String filtroUsername, String filtroNombre, String filtroApellidos, List<String> rolesName, String mostrarOcultos) {
        List<Long> roles = null;
        List<Usuario> usuarios = new ArrayList<>();
        List<RolDao_ListarUsuarios> convertedRol = new ArrayList<>();

        for (Rol rol : Rol.values()) {
            convertedRol.add(RolDao_ListarUsuarios.crearDao(rol,false));
        }

        if (rolesName != null && !rolesName.isEmpty()) {
            List<Long> rolesLambda = new ArrayList<>();
            List<String> rolesLC = rolesName.stream().map(java.lang.String::toLowerCase).toList();

            convertedRol.forEach(rol -> {
                rol.setSelected(rolesLC.contains(rol.getName().toLowerCase()));
            });

            rolesName.forEach(role -> {
                switch (role) {
                    case "admin" -> rolesLambda.add(0L);
                    case "tecnico" -> rolesLambda.add(1L);
                    case "reporante" -> rolesLambda.add(2L);
                }
            });
            roles = rolesLambda;
        }



        usuarios = repositorio.findByFilters(filtroUsername,filtroNombre,filtroApellidos,mostrarOcultos.equals("on"),roles);
        usuarios = procesarOrden(usuarios,ordenPor,ordenAsc);
        usuarios = procesarPaginacion(usuarios,model,paginaNum,perPageNum);

        model.addAttribute("mostrandoAhora", usuarios.size());
        model.addAttribute("roles",convertedRol);
        model.addAttribute("mostrarOcultos", mostrarOcultos.equals("on"));
        model.addAttribute("usuarios", usuarios.stream().map(UsuarioDao_Listar::crearDao).toList());

        return "admin/usuario/listar";
    }

    private List<Usuario> procesarOrden(List<Usuario> usuarios, String ordenPor, boolean ordenAsc) {

        if (ordenPor != null) {
            Comparator<Usuario> comparator = null;
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

        return  usuarios;

    }

    public String cargarCrear(Model model) {
        if (!model.containsAttribute("usuarioDao")) {
            model.addAttribute("usuarioDao", new UsuarioDao_Crear());
        }
        model.addAttribute("roles", Rol.values());
        model.addAttribute("modificar", false);
        return "admin/usuario/formulario";
    }

    public String cargarModificar(Model model, long id,RedirectAttributes redirectAttributes) {
        Usuario objetivo = findById(id).orElseThrow();
        if (!objetivo.isEditable()) {
        	redirectAttributes.addFlashAttribute("error","No puedes modificar el usuario seleccionado.");
            return "redirect:/usuarios";
        }

        if (!model.containsAttribute("usuarioDao")) {
            model.addAttribute("usuarioDao", UsuarioDao_Modificar.crearDao(objetivo));
        }

        model.addAttribute("roles", Rol.values());
        model.addAttribute("modificar", true);
        return  "admin/usuario/formulario";
    }

    public String eliminar(long id,RedirectAttributes redirectAttributes) {
        Usuario objetivo = findById(id).orElseThrow();
        Usuario userDefault;
        if (!objetivo.isEditable()) {
        	redirectAttributes.addFlashAttribute("error","No puedes eliminar el usuario seleccionado.");
            return "redirect:/usuarios"; // Con esto evitamos que se editen los sin-tecnico/sin-reportante
        }

        if (objetivo.getRol().equals(Rol.tecnico)) {
            userDefault = repositorio.findByUsername("sin-tecnico");
        } else if (objetivo.getRol().equals(Rol.reportante)) {
            userDefault = repositorio.findByUsername("sin-reportante");
        } else {
            userDefault = null; // NUNCA DEBERA LLEGAR AQUÍ SI NADIE TOCA LA BD PARA MAL O ALGO ASI.
            return "redirect:/usuarios";
        }

        objetivo.transferirIncidencias(userDefault);
        delete(objetivo);
        return "redirect:/usuarios";
    }

    public String modificar(UsuarioDao_Modificar usuarioDao,Model model) {
        Usuario usuarioAntiguo = findById(usuarioDao.getId()).orElseThrow();
        Usuario nuevoUsuario = revertirDao(usuarioDao,usuarioAntiguo);
        if (nuevoUsuario != null) {
            if (repositorio.findByUsername(nuevoUsuario.getUsername()) != null && !usuarioAntiguo.getUsername().equals(nuevoUsuario.getUsername())) {
                model.addAttribute("usuarioDao", usuarioDao);
                model.addAttribute("error","El nombre de usuario ya existe.");
                return cargarCrear(model);
            }
            if (nuevoUsuario.getRol() == null) {
                model.addAttribute("usuarioDao", usuarioDao);
                model.addAttribute("error","Debe escoger un rol.");
                return cargarCrear(model);
            }
        }

        edit(nuevoUsuario);
        return "redirect:/usuarios";
    }

    public String crear(UsuarioDao_Crear usuarioDao,Model model) {
        Usuario nuevoUsuario = usuarioDao.revertirDao();
        if (nuevoUsuario != null) {
            if (repositorio.findByUsername(nuevoUsuario.getUsername()) != null) {
                model.addAttribute("usuarioDao", usuarioDao);
                model.addAttribute("error","El nombre de usuario ya existe.");
                return cargarCrear(model);
            };
            if (nuevoUsuario.getRol() == null) {
                model.addAttribute("usuarioDao", usuarioDao);
                model.addAttribute("error","Debe escoger un rol.");
                return cargarCrear(model);
            }
        }
        save(nuevoUsuario);
        return "redirect:/usuarios";
    }
}
