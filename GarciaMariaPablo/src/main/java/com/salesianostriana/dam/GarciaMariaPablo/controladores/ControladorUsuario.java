package com.salesianostriana.dam.GarciaMariaPablo.controladores;

import com.salesianostriana.dam.GarciaMariaPablo.daos.usuario.UsuarioDao_Crear;
import com.salesianostriana.dam.GarciaMariaPablo.daos.usuario.UsuarioDao_Modificar;
import com.salesianostriana.dam.GarciaMariaPablo.servicios.ServicioUsuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class ControladorUsuario {

    @Autowired
    private ServicioUsuario servicioUsuario;

    public void activeTab(Model model, HttpServletRequest request) {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            model.addAttribute("activeTab", "usuarios");
        }
    }

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

    @GetMapping("/{id}")
    public String cargarModificar(Model model, @PathVariable long id) {
        return servicioUsuario.cargarModificar(model,id);
    }

    @PostMapping
    public String crear(@ModelAttribute UsuarioDao_Crear usuarioDao) {
        return servicioUsuario.crear(usuarioDao);
    }

    @PutMapping
    public String modificar(@ModelAttribute UsuarioDao_Modificar usuarioDao) {
        return servicioUsuario.modificar(usuarioDao);
    }

    @DeleteMapping("{id}")
    public String eliminar(@PathVariable long id) {
        return servicioUsuario.eliminar(id);
    }

}
