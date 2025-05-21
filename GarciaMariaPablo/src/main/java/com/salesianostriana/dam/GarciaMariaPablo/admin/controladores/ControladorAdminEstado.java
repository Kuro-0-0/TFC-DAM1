package com.salesianostriana.dam.GarciaMariaPablo.admin.controladores;


import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.estado.EstadoDao_Crear;
import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.estado.EstadoDao_Modificar;
import com.salesianostriana.dam.GarciaMariaPablo.admin.servicios.ServicioAdminEstado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/estados")
public class ControladorAdminEstado {

    @Autowired
    private ServicioAdminEstado servicioEstado;

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

    @GetMapping("/modificar/{id}")
    public String cargarModificar(Model model, @PathVariable long id, RedirectAttributes redirectAttributes) {
        return servicioEstado.cargarModificar(model,id,redirectAttributes);
    }

    @PostMapping
    public String crear(EstadoDao_Crear estadoDao, Model model) {
        return servicioEstado.crear(estadoDao, model);
    }

    @PutMapping
    public String modificar(EstadoDao_Modificar estadoDao) {
        return servicioEstado.modificar(estadoDao);
    }

    @PutMapping("/alternar/{id}")
    public String altenarActivo(@PathVariable long id, RedirectAttributes redirectAttributes) {
        return servicioEstado.alternarActivo(id,redirectAttributes);
    }

    @DeleteMapping
    public String eliminar(Model model) {
        return null;
    }


}
