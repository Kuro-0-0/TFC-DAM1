package com.salesianostriana.dam.GarciaMariaPablo.modelos;

import com.salesianostriana.dam.GarciaMariaPablo.modelos.utilidades.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Usuario implements Comparable<Usuario> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;

    private String nombre;
    private String apellidos;

    private Rol rol;
    private boolean editable;

    @OneToMany
    @JoinColumn(name = "reportante_id")
    private List<Incidencia> incidenciasReportadas;

    @OneToMany
    @JoinColumn(name = "tecnico_id")
    private List<Incidencia> incidenciasGestionadas;

    @Override
    public int compareTo(Usuario o) {
        String thisNombreCompleto = this.getNombre() + " " + this.getApellidos();
        String oNombreCompleto = o.getNombre() + " " + o.getApellidos();
        return thisNombreCompleto.toLowerCase().compareTo(oNombreCompleto.toLowerCase());
    }

    /* HELPERS */

    public void transferirIncidencias(Usuario userDefault) {
        switch (userDefault.getRol()) {
            case tecnico -> this.incidenciasGestionadas.forEach( incidencia -> incidencia.setTecnico(userDefault));
            case reportante -> this.incidenciasReportadas.forEach( incidencia -> incidencia.setReportante(userDefault));
        }
    }

}
