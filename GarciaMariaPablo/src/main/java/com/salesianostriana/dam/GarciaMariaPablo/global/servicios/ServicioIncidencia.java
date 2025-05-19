package com.salesianostriana.dam.GarciaMariaPablo.global.servicios;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Incidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.repositorios.RepositorioHistorialEstados;
import com.salesianostriana.dam.GarciaMariaPablo.global.repositorios.RepositorioIncidencia;
import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.otros.base.ServicioBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioIncidencia extends ServicioBaseImpl<Incidencia,Long, RepositorioIncidencia> {

    @Autowired
    RepositorioHistorialEstados repositorioHistorialEstados;

    public boolean eliminar(long id) {
        Incidencia i = findById(id).orElseThrow();
        i.eliminarEstado();
        i.eliminarUsuarios();
        i.getHistorialEstados().forEach(e -> {
            e.setIncidencia(null);
            repositorioHistorialEstados.save(e);
        });
        i.setHistorialEstados(null);
        delete(i);
        return true;
    }

    public List<Incidencia> getIncidenciasPorIdReportante(Long id) {
        return repositorio.getIncidenciasPorIdReportante(id);
    }

    public List<Incidencia> getIncidenciasResueltasPorIdTecnico(Long id) {
        return repositorio.getIncidenciasResueltasPorIdTecnico(id);
    }

    public List<Incidencia> getIncidenciasResueltas() {
        return repositorio.getIncidenciasResueltas();
    }
}
