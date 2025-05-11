package com.salesianostriana.dam.GarciaMariaPablo.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.daos.otros.RolDao_ListarUsuarios;
import com.salesianostriana.dam.GarciaMariaPablo.daos.usuario.UsuarioDao_Listar;
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
}
