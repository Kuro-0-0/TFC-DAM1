package com.salesianostriana.dam.GarciaMariaPablo.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.daos.otros.RolDao_ListarUsuarios;
import com.salesianostriana.dam.GarciaMariaPablo.daos.usuario.UsuarioDao_Crear;
import com.salesianostriana.dam.GarciaMariaPablo.daos.usuario.UsuarioDao_Listar;
import com.salesianostriana.dam.GarciaMariaPablo.daos.usuario.UsuarioDao_Modificar;
import com.salesianostriana.dam.GarciaMariaPablo.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.modelos.utilidades.Rol;
import com.salesianostriana.dam.GarciaMariaPablo.repositorios.RepositorioUsuario;
import com.salesianostriana.dam.GarciaMariaPablo.servicios.base.ServicioBase;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ServicioUsuario extends ServicioBase<Usuario, Long, RepositorioUsuario> {

    public Usuario revertirDao(UsuarioDao_Modificar usuarioDao) {

        Usuario original = findById(usuarioDao.getId()).orElseThrow();
        String password = usuarioDao.getPassword().isEmpty() ? original.getPassword() : usuarioDao.getPassword();

        original.setUsername(usuarioDao.getUsername());
        original.setPassword(password);
        original.setNombre(usuarioDao.getNombre());
        original.setApellidos(usuarioDao.getApellidos());
        original.setRol(usuarioDao.getRol());

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
            List<String> rolesLC = rolesName.stream().map(String::toLowerCase).toList();

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
        model.addAttribute("usuarioDao", new UsuarioDao_Crear());
        model.addAttribute("roles", Rol.values());
        model.addAttribute("modificar", false);
        return "admin/usuario/formulario";
    }

    public String cargarModificar(Model model, long id) {
        Usuario objetivo = findById(id).orElseThrow();
        if (!objetivo.isEditable()) {
            return "redirect:/usuarios";
        }
        model.addAttribute("usuarioDao", UsuarioDao_Modificar.crearDao(objetivo));
        model.addAttribute("roles", Rol.values());
        model.addAttribute("modificar", true);
        return  "admin/usuario/formulario";
    }

    public String eliminar(long id) {
        Usuario objetivo = findById(id).orElseThrow();
        Usuario userDefault;
        if (!objetivo.isEditable()) {
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

    public String modificar(UsuarioDao_Modificar usuarioDao) {
        edit(revertirDao(usuarioDao));
        return "redirect:/usuarios";
    }

    public String crear(UsuarioDao_Crear usuarioDao) {
        save(usuarioDao.revertirDao());
        return "redirect:/usuarios";
    }
}
