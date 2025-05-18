package com.salesianostriana.dam.GarciaMariaPablo.user.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.global.daos.estado.external.EstadoDao_Seleccionar;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.incidencia.IncidenciaDao_Inspeccionar;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.incidencia.IncidenciaDao_Listar;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Estado;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Incidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Usuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.TipoEstados;
import com.salesianostriana.dam.GarciaMariaPablo.global.repositorios.RepositorioIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.seguridad.ServicioSeguridad;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.ServicioEstado;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.ServicioIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.ServicioUsuario;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.otros.base.ServicioBaseImpl;
import com.salesianostriana.dam.GarciaMariaPablo.user.daos.incidencia.IncidenciaUserDao_Crear;
import com.salesianostriana.dam.GarciaMariaPablo.user.daos.incidencia.IncidenciaUserDao_Modificar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServicioUserIncidencias extends ServicioBaseImpl<Incidencia,Long, RepositorioIncidencia> {

    @Autowired
    private ServicioSeguridad seguridad;

    @Autowired
    private ServicioUserEstado servicioUserEstado;

    @Autowired
    ServicioUsuario servicioUsuario;

    @Autowired
    private ServicioIncidencia servicioIncidencia;
    @Autowired
    private ServicioEstado servicioEstado;

    public Incidencia revertirDao(IncidenciaUserDao_Crear incidenciaDao) {
        Usuario reportante = seguridad.obtenerUsuarioLogado();
        Usuario tecnico = servicioUsuario.getSinTecnico();

        return Incidencia.builder()
                .titulo(incidenciaDao.getTitulo())
                .descripcion(incidenciaDao.getDescripcion())
                .ubicacion(incidenciaDao.getUbicacion())
                .fechaModificacion(LocalDateTime.now())
                .fechaIEA(LocalDateTime.now())
                .estado(servicioEstado.getSinEstado())
                .reportante(reportante)
                .tecnico(tecnico)
                .build();
    }

    public Incidencia revertirDao(IncidenciaUserDao_Modificar incidenciaDao) {
        Incidencia original = findById(incidenciaDao.getId()).orElseThrow();
        return Incidencia.builder()
                .id(incidenciaDao.getId())
                .titulo(incidenciaDao.getTitulo())
                .ubicacion(incidenciaDao.getUbicacion())
                .descripcion(incidenciaDao.getDescripcion())
                .fechaModificacion(LocalDateTime.now())
                .fechaIEA(original.getFechaIEA())
                .tecnico(original.getTecnico())
                .reportante(original.getReportante())
                .estado(original.getEstado())
                .build();
    }


    public String inspeccionar(long id, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = seguridad.obtenerUsuarioLogado();
        Incidencia incidencia = findById(id).orElseThrow();
        if (incidencia.getReportante().getId() != usuario.getId()) {
            redirectAttributes.addFlashAttribute("error","No puedes inspeccionar incidencias que no son de tu propiedad.");
            return "redirect:/user/dashboard";
        }

        model.addAttribute("usuarioRol", "USER");
        model.addAttribute("incidencia", IncidenciaDao_Inspeccionar.crearDao(incidencia));

        return "user/incidencia/inspeccionar";
    }

    public String listar(Model model, RedirectAttributes redirectAttributes,String buscar, String perPage,String paginaNum,String estadoValue) {
        Usuario usuario = seguridad.obtenerUsuarioLogado();
        int estadisticasRapidas[] = {0,0,0};
        List<Incidencia> incidencias = servicioIncidencia.getIncidenciasPorIdReportante(usuario.getId());
        List <IncidenciaDao_Listar> listaFinal;

        model.addAttribute("totalIncidencias", incidencias.size());
        incidencias
                .stream()
                .map(Incidencia::getEstado)
                .map(Estado::getTipo)
                .forEach(t -> {
                    switch (t) {
                        case TipoEstados.Comienzo -> estadisticasRapidas[0]++;
                        case TipoEstados.Proceso -> estadisticasRapidas[1]++;
                        case TipoEstados.Final -> estadisticasRapidas[2]++;
                        default -> {
                            System.out.println("Tipo no valido" + t);
                        }
                    }
                });

        listaFinal = incidencias.stream()
                .filter(i -> buscar != null ? i.getTitulo().toLowerCase().contains(buscar.toLowerCase()) : true)
                .filter(i -> estadoValue != null && !estadoValue.isBlank() ? i.getEstado().getValor().equals(estadoValue) : true)
                .map(IncidenciaDao_Listar::crearDao)
                .toList();

        model.addAttribute("estados", servicioUserEstado
                .findAll()
                .stream()
                .map(EstadoDao_Seleccionar::crearDao)
                .toList());

        model.addAttribute("datosIncidencias", estadisticasRapidas);

        incidencias = procesarPaginacion(incidencias,model,paginaNum,perPage);
        model.addAttribute("mostrandoAhora",incidencias.size());
        model.addAttribute("incidencias",listaFinal);

        return "user/incidencia/listar";
    }

    public String eliminar(long id, RedirectAttributes redirectAttributes) {
        Usuario usuario = seguridad.obtenerUsuarioLogado();
        if (!servicioIncidencia.getIncidenciasPorIdReportante(usuario.getId())
                .stream()
                .map(Incidencia::getId)
                .toList()
                .contains(id)) {
            redirectAttributes.addFlashAttribute("error","No puedes eliminar una incidencia que no te pertenece.");
            return "redirect:/user/incidencias";
        }
        if (!servicioIncidencia.eliminar(id)) {
            redirectAttributes.addFlashAttribute("error","Algo ha salido mal durante la eliminacion de la incidencia.");
        }
        return "redirect:/user/incidencias";
    }

    public String cargarCrear(Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("incidenciaDao", new IncidenciaUserDao_Crear());
        model.addAttribute("modificar",false);
        return "user/incidencia/formulario";
    }

    public String crear(Model model, RedirectAttributes redirectAttributes, IncidenciaUserDao_Crear incidenciaDao) {
        save(revertirDao(incidenciaDao));
        return "redirect:/user/incidencias";
    }


    public String cargarModificar(long id, Model model, RedirectAttributes redirectAttributes) {
        Incidencia incidencia = servicioIncidencia.findById(id).orElseThrow();
        Usuario usuario = seguridad.obtenerUsuarioLogado();
        if (!servicioIncidencia.getIncidenciasPorIdReportante(usuario.getId()).contains(incidencia)){
            redirectAttributes.addFlashAttribute("error","No puedes editar una incidencia que no te pertenece.");
            return "redirect:/user/incidencias";
        };
        if (incidencia.getEstado().getTipo().equals(TipoEstados.Final)) {
            redirectAttributes.addFlashAttribute("error","No puedes editar una incidencia ya cerrada, por favor cree una nueva.");
        }
        model.addAttribute("incidenciaDao", IncidenciaUserDao_Modificar.crearDao(incidencia));
        model.addAttribute("modificar",true);
        return "user/incidencia/formulario";
    }

    public String modificar(IncidenciaUserDao_Modificar incidenciaDao, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = seguridad.obtenerUsuarioLogado();
        if (!servicioIncidencia.getIncidenciasPorIdReportante(usuario.getId()).stream().map(Incidencia::getId).toList().contains(incidenciaDao.getId())){
            redirectAttributes.addFlashAttribute("error","No puedes editar una incidencia que no te pertenece.");
            return "redirect:/user/incidencias";
        }
        edit(revertirDao(incidenciaDao));
        return "redirect:/user/incidencias";
    }

}
