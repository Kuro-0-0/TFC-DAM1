package com.salesianostriana.dam.GarciaMariaPablo.global.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDateTime fechaIEA;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

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

    @OneToMany(mappedBy = "incidencia")
    @ToString.Exclude
    private List<HistorialEstados> historialEstados = new ArrayList<>();

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
            case USER -> {
                this.reportante = usuario;
                usuario.getIncidenciasReportadas().add(this);
            }
            case TECH -> {
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

    public void anadirRegistroHistorialEstados(HistorialEstados nuevaEntrada) {
        if (historialEstados == null) {
            historialEstados = new ArrayList<>();
        }
        this.historialEstados.add(nuevaEntrada);
        nuevaEntrada.setIncidencia(this);
    }

}
