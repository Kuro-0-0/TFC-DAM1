package com.salesianostriana.dam.GarciaMariaPablo.controladores;

import com.salesianostriana.dam.GarciaMariaPablo.daos.estado.EstadoDao_Crear;
import com.salesianostriana.dam.GarciaMariaPablo.daos.estado.EstadoDao_Modificar;
import com.salesianostriana.dam.GarciaMariaPablo.servicios.ServicioEstado;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/estados")
public class ControladorEstado {

    @Autowired
    private ServicioEstado servicioEstado;

    public void activeTab(Model model, HttpServletRequest request) {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            model.addAttribute("activeTab", "estados");
        }
    }

    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false,name = "pagina",defaultValue = "1") String paginaNum,
                         @RequestParam(required = false,name = "perPage",defaultValue = "10") String perPageNum,
                         @RequestParam(required = false,name = "ordenarPor", defaultValue = "id") String ordenPor,
                         @RequestParam(required = false,name = "ordenAsc", defaultValue = "true") Boolean ordenAsc,
                         @RequestParam(required = false,name = "buscar", defaultValue = "") String buscar
                         ) {
        return servicioEstado.listar(model,paginaNum,perPageNum,ordenPor,ordenAsc,buscar);
    };

    @GetMapping("/crear")
    public String cargarCrear(Model model) {
        return servicioEstado.cargarCrear(model);
    }

    @GetMapping("/{id}")
    public String cargarModificar(Model model, @PathVariable long id) {
        return servicioEstado.cargarModificar(model,id);
    }

    @PostMapping
    public String crear(EstadoDao_Crear estadoDao) {
        return servicioEstado.crear(estadoDao);
    }

    @PutMapping
    public String modificar(EstadoDao_Modificar estadoDao) {
        return servicioEstado.modificar(estadoDao);
    }

    @PutMapping("/{id}")
    public String altenarActivo(@PathVariable long id) {
        return servicioEstado.alternarActivo(id);
    }

    @DeleteMapping
    public String eliminar(Model model) {
        return null;
    }


}
