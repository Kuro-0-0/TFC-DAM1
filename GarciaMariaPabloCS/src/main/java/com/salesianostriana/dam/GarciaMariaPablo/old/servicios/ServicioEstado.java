package com.salesianostriana.dam.GarciaMariaPablo.old.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.old.daos.estado.EstadoDao_Crear;
import com.salesianostriana.dam.GarciaMariaPablo.old.daos.estado.EstadoDao_Listar;
import com.salesianostriana.dam.GarciaMariaPablo.old.daos.estado.EstadoDao_Modificar;
import com.salesianostriana.dam.GarciaMariaPablo.old.modelos.Estado;
import com.salesianostriana.dam.GarciaMariaPablo.old.modelos.utilidades.TipoEstados;
import com.salesianostriana.dam.GarciaMariaPablo.old.repositorios.RepositorioEstado;
import com.salesianostriana.dam.GarciaMariaPablo.old.servicios.base.ServicioBaseImpl;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.text.Normalizer;
import java.util.Comparator;
import java.util.List;

@Service
public class ServicioEstado extends ServicioBaseImpl<Estado, Long, RepositorioEstado> {

    public Estado revertirDao(EstadoDao_Modificar estadoDao) {
        Estado estado = findById(estadoDao.getId()).orElseThrow();

        String valor = estadoDao.getNombre().toLowerCase().replace(" ", "-");
        valor = Normalizer.normalize(valor, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

        return Estado.builder()
                .id(estadoDao.getId())
                .nombre(estadoDao.getNombre())
                .valor(valor)
                .colorFondo(estadoDao.getColorFondo())
                .colorTexto(estadoDao.getColorTexto())
                .activo(estadoDao.isActivo())
                .tipo(TipoEstados.valueOf(estadoDao.getTipo()))
                .build();
    }

    public String listar(Model model, String paginaNum, String perPageNum, String ordenPor, Boolean ordenAsc, String buscar) {
        List<Estado> estados = repositorio.findByText(buscar);
        estados = procesarOrden(estados, model, ordenPor, ordenAsc);
        estados = procesarPaginacion(estados, model, paginaNum, perPageNum);

        model.addAttribute("mostrandoAhora", estados.size());
        model.addAttribute("estados", estados.stream()
                .map(EstadoDao_Listar::crearDao)
                .toList());

        return "admin/estado/listar";
    }

    private List<Estado> procesarOrden(List<Estado> estados, Model model, String ordenPor, Boolean ordenAsc) {
        if (ordenPor != null) {
            Comparator<Estado> comparator = null;
            switch (ordenPor) {
                case "activo" -> {
                    comparator = Comparator.comparing(Estado::isActivo);
                }
                default -> {
                    comparator = Comparator.comparing(Estado::getNombre);
                }
            }
            if (!ordenAsc) {
                comparator = comparator.reversed();
            }
            estados.sort(comparator);
        }
        return estados;

    }

    public String cargarCrear(Model model) {
    	if (!model.containsAttribute("estadoDao")) {
            model.addAttribute("estadoDao", new EstadoDao_Crear());
		}
        model.addAttribute("tipos", TipoEstados.values());
        model.addAttribute("modificar", false);
        return "admin/estado/formulario";
    }

    public String cargarModificar(Model model, long id) {
        model.addAttribute("estadoDao", EstadoDao_Modificar.crearDao(findById(id).orElseThrow()));
        model.addAttribute("tipos", TipoEstados.values());
        model.addAttribute("modificar", true);
        return "admin/estado/formulario";
    }


    public String crear(EstadoDao_Crear estadoDao, Model model) {
        Estado nuevoEstado = estadoDao.revertirDao();
        if (!repositorio.findByNombre(estadoDao.getNombre()).isEmpty()) {
        	model.addAttribute("estadoDao", nuevoEstado);
        	model.addAttribute("error", "No puedes crear dos estados con el mismo nombre.");
			return cargarCrear(model);
		}
    	save(nuevoEstado);
        return "redirect:/estados";
    }

    public String modificar(EstadoDao_Modificar estadoDao) {
        edit(revertirDao(estadoDao));
        return "redirect:/estados";
    }

    public String alternarActivo(long id) {
        Estado objetivo = findById(id).orElseThrow();
        objetivo.setActivo(!objetivo.isActivo());
        save(objetivo);
        return "redirect:/estados";
    }
}
