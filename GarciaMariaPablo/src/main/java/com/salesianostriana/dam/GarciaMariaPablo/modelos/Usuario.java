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
    @Column(unique = true, nullable = false)
    private long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String nombre;
    private String apellidos;

    private Rol rol;
    @Builder.Default
    private boolean editable = true;

    @OneToMany(mappedBy = "reportante")
    private List<Incidencia> incidenciasReportadas;

    @OneToMany(mappedBy = "tecnico")
    private List<Incidencia> incidenciasGestionadas;

    @Override
    public int compareTo(Usuario u) {
        String thisNombreCompleto = this.getNombre() + " " + this.getApellidos();
        String uNombreCompleto = u.getNombre() + " " + u.getApellidos();
        return thisNombreCompleto.toLowerCase().compareTo(uNombreCompleto.toLowerCase());
    }

    /* HELPERS */

    public void transferirIncidencias(Usuario userDefault) {
        switch (userDefault.getRol()) {
            case tecnico -> this.incidenciasGestionadas.forEach( incidencia ->  {incidencia.setTecnico(userDefault);userDefault.getIncidenciasGestionadas().add(incidencia);});
            case reportante -> this.incidenciasReportadas.forEach( incidencia -> {incidencia.setReportante(userDefault);userDefault.getIncidenciasReportadas().add(incidencia);});
        }
    }

}
