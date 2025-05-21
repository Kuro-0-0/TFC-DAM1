package com.salesianostriana.dam.GarciaMariaPablo.admin.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.estado.EstadoDao_Crear;
import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.estado.EstadoDao_Listar;
import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.estado.EstadoDao_Modificar;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Estado;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.TipoEstados;
import com.salesianostriana.dam.GarciaMariaPablo.global.repositorios.RepositorioEstado;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.otros.base.ServicioBaseImpl;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.Normalizer;
import java.util.Comparator;
import java.util.List;

@Service
public class ServicioAdminEstado extends ServicioBaseImpl<Estado, Long, RepositorioEstado> {

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
        int estadosTotal;
        estados = procesarOrden(estados, model, ordenPor, ordenAsc);
        estadosTotal = estados.size();
        estados = procesarPaginacion(estados, model, paginaNum, perPageNum);

        model.addAttribute("estadosTotal", estadosTotal);
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

    public String cargarModificar(Model model, long id, RedirectAttributes redirectAttributes) {
        Estado estado = repositorio.findById(id).orElseThrow();
        if (!model.containsAttribute("estadoDao")) {
            model.addAttribute("estadoDao", EstadoDao_Modificar.crearDao(estado));
        }
        model.addAttribute("tipos", TipoEstados.values());
        model.addAttribute("modificar", true);

        if (estado.getValor().equals("sin-estado")) {
            redirectAttributes.addFlashAttribute("error","No puedes editar esta incidencia.");
            return "redirect:/admin/estados";
        }
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
        return "redirect:/admin/estados";
    }

    public String modificar(EstadoDao_Modificar estadoDao, RedirectAttributes redirectAttributes, Model model) {
        Estado nuevoEstado = revertirDao(estadoDao);
        Estado antiguaIncidencia = repositorio.findById(nuevoEstado.getId()).orElseThrow();
        if (!repositorio.findByNombre(estadoDao.getNombre()).isEmpty() && !antiguaIncidencia.getNombre().equals(nuevoEstado.getNombre())) {
            model.addAttribute("error", "Ya existe una incidencia con el mismo nombre");
            model.addAttribute("estadoDao", estadoDao);
            return cargarModificar(model,estadoDao.getId(),redirectAttributes);
        }
        edit(nuevoEstado);
        return "redirect:/admin/estados";
    }

    public String alternarActivo(long id, RedirectAttributes redirectAttributes) {
        Estado objetivo = findById(id).orElseThrow();
        if (objetivo.getValor().equals("sin-estado")) {
            redirectAttributes.addFlashAttribute("error","No puedes editar esta incidencia.");
            return "redirect:/admin/estados";
        }
        objetivo.setActivo(!objetivo.isActivo());
        save(objetivo);
        return "redirect:/admin/estados";
    }
}
