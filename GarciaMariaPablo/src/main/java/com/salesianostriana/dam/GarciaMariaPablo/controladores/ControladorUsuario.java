package com.salesianostriana.dam.GarciaMariaPablo.controladores;

import com.salesianostriana.dam.GarciaMariaPablo.daos.usuario.UsuarioDao_Crear;
import com.salesianostriana.dam.GarciaMariaPablo.daos.usuario.UsuarioDao_Modificar;
import com.salesianostriana.dam.GarciaMariaPablo.servicios.ServicioUsuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class ControladorUsuario {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false, name = "pagina"		,defaultValue = "1") String paginaNum,
                         @RequestParam(required = false, name = "perPage"	,defaultValue = "10") String perPageNum,
                         @RequestParam(required = false, name = "ordenarPor"	, defaultValue = "username") String ordenPor,
                         @RequestParam(required = false, name = "ordenAsc"	, defaultValue = "false") boolean ordenAsc,
                         @RequestParam(required = false, name = "filtroUsername"	, defaultValue = "") String filtroUsername,
                         @RequestParam(required = false, name = "filtroNombre"	, defaultValue = "") String filtroNombre,
                         @RequestParam(required = false, name = "filtroApellidos", defaultValue = "") String filtroApellidos,
                         @RequestParam(required = false, name = "rolesSeleccionados") List<String> roles,
                         @RequestParam(required = false,name = "mostrarOcultos", defaultValue = "") String mostrarOcultos
                        ) {
        return servicioUsuario.listar(model,paginaNum,perPageNum,ordenPor,ordenAsc,filtroUsername,filtroNombre,filtroApellidos,roles,mostrarOcultos);
    };

    @GetMapping("/crear")
    public String cargarCrear(Model model) {
        return servicioUsuario.cargarCrear(model);
    }

    @GetMapping("/modificar/{id}")
    public String cargarModificar(Model model, @PathVariable long id,RedirectAttributes redirectAttributes) {
        return servicioUsuario.cargarModificar(model,id, redirectAttributes);
    }

    @PostMapping
    public String crear(@ModelAttribute UsuarioDao_Crear usuarioDao, Model model) {
        return servicioUsuario.crear(usuarioDao,model);
    }

    @PutMapping
    public String modificar(@ModelAttribute UsuarioDao_Modificar usuarioDao, Model model) {
        return servicioUsuario.modificar(usuarioDao, model);
    }

    @DeleteMapping("{id}")
    public String eliminar(@PathVariable long id,RedirectAttributes redirectAttributes) {
        return servicioUsuario.eliminar(id, redirectAttributes);
    }

}
