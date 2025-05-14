package com.salesianostriana.dam.GarciaMariaPablo.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    private String titulo;

    @Column(length = 500)
    private String descripcion;
    private String ubicacion;
    private LocalDate fechaCreacion;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "reportante_id")
    private Usuario reportante;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "tecnico_id")
    private Usuario tecnico;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    @ToString.Exclude
    private Estado estado;

    /* HELPERS */

    public void anadirEstado(Estado estado) {
        this.estado = estado;
        estado.getIncidencias().add(this);
    }


    public void eliminarEstado() {
        estado.getIncidencias().remove(this);
        estado = null;
    }


    public void anadirUsuario(Usuario usuario) {
        switch (usuario.getRol()) {
            case reportante -> {
                this.reportante = usuario;
                usuario.getIncidenciasReportadas().add(this);
            }
            case tecnico -> {
                this.tecnico = usuario;
                usuario.getIncidenciasGestionadas().add(this);
            }
            default -> throw new IllegalArgumentException("No se puede asignar incidencias a usuarios de rol :"+usuario.getRol() );
        }
    }

    public void eliminarUsuarios() {
        tecnico.getIncidenciasGestionadas().remove(this);
        reportante.getIncidenciasGestionadas().remove(this);
        tecnico = null;
        reportante = null;
    }

    public String getNombreEstado() {
        return estado.getNombre();
    }
}
